package iuh.fit.se.service.Impl;

import iuh.fit.se.constant.Constant;
import iuh.fit.se.dto.EnrollRequest;
import iuh.fit.se.entity.Enroll;
import iuh.fit.se.entity.Student;
import iuh.fit.se.entity.Unit;
import iuh.fit.se.event.RegisterSuccessEvent;
import iuh.fit.se.exception.ResourceNotFoundException;
import iuh.fit.se.repository.EnrollRepository;
import iuh.fit.se.repository.UnitRepository;
import iuh.fit.se.service.EnrollService;
import lombok.AllArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class EnrollServiceImpl implements EnrollService {
    private final EnrollRepository enrollRepository;
    private final UnitRepository unitRepository;
    private final WebClient.Builder webClientBuilder;
    private final KafkaTemplate<String, RegisterSuccessEvent> kafkaTemplate;


    public Student getStudent(String studentCode){

        return webClientBuilder.build().get()
                .uri("http://student-service/api/student/{code}",studentCode)
                .retrieve()
                .bodyToMono(Student.class).block();
    }

    public Mono<Student> fallbackForGetStudent(String studentCode, WebClientResponseException e) {
        return Mono.empty();
    }

    @Override
    public Boolean register(EnrollRequest enrollRequest) {
        Student student = getStudent(enrollRequest.getStudentCode());

        if(student == null){
            throw new ResourceNotFoundException("STUDENT",  "CODE", enrollRequest.getStudentCode());
        }

        Unit unit = unitRepository.findById(enrollRequest.getUnitId()).orElseThrow(() -> new ResourceNotFoundException("UNIT",  "ID",enrollRequest.getUnitId()));

//        Unit unit = webClientBuilder.build().get()
//                .uri("http://course-service/api/unit/{id}", enrollRequest.getUnitId())
//                .retrieve()
//                .bodyToMono(Unit.class).block();

        if(unit == null){
            throw new ResourceNotFoundException("UNIT",  "ID", enrollRequest.getUnitId());
        }

        if(unit.getStartDate().isBefore(LocalDate.now())){
            throw new IllegalStateException("Class registration is closed");
        }

        int countEnrollments = enrollRepository.countEnrollmentsByUnit(unit);
        if(countEnrollments >= unit.getNumberOfStudent()){
            throw new IllegalStateException("The class has been fully paid");
        }

        //Check theory schedule
        int periodPerLesson = unit.getSchedule().getPeriodEnd() - unit.getSchedule().getPeriodStart() + 1;
        int totalTheoryPeriod = Constant.PERIOD_PER_THEORY_CREDIT * unit.getSubject().getTheoryCredit();
        int totalLesson = (int) Math.ceil(totalTheoryPeriod / periodPerLesson);
        LocalDate endTheory = unit.getStartDate().plusWeeks(totalLesson);
        LocalDate endDate = endTheory.plusWeeks(Constant.BUFFER_TIME);
        List<Long> checkScheduleWithTheorySchedule = enrollRepository.selectUnitIdsByStudentAndSchedule(student, unit.getSchedule().getDayOfWeek(),
                unit.getSchedule().getPeriodStart(), unit.getSchedule().getPeriodEnd(), unit.getStartDate(), endDate
        );
        List<Long> checkSchedule = new ArrayList<>();
        if(unit.getPracticeSchedule() != null){
             checkSchedule = enrollRepository.selectUnitIdsByStudentAndSchedule(student, unit.getPracticeSchedule().getDayOfWeek(),
                    unit.getPracticeSchedule().getPeriodStart(), unit.getPracticeSchedule().getPeriodEnd(), unit.getStartDate(), endDate
            );
        }


        checkSchedule  = Stream.concat(checkSchedule.stream(), checkScheduleWithTheorySchedule.stream()).toList();


        //Check practice schedule
        List<Long> checkPracticeSchedule = new ArrayList<>();
        if(unit.getPracticeSchedule()  != null){
            int periodPerPracticeLesson = unit.getPracticeSchedule().getPeriodEnd() - unit.getPracticeSchedule().getPeriodStart() + 1;
            int totalPracticePeriod = Constant.PERIOD_PER_PRACTICE_CREDIT * unit.getSubject().getPracticeCredit();
            int totalPracticeLesson = (int) Math.ceil(totalPracticePeriod / periodPerPracticeLesson);

            LocalDate startPractice = unit.getStartDate().plusWeeks(Constant.THEORY_TO_PRACTICE_TIME);
            LocalDate endPractice = startPractice.plusWeeks(totalPracticeLesson+Constant.BUFFER_TIME);
            List<Long> checkPracticeSchedule1 = enrollRepository.selectUnitIdsByStudentAndPracticeSchedule(student, unit.getSchedule().getDayOfWeek(),
                    unit.getSchedule().getPeriodStart(), unit.getSchedule().getPeriodEnd(), startPractice, endPractice
            );

                checkPracticeSchedule = enrollRepository.selectUnitIdsByStudentAndPracticeSchedule(student, unit.getPracticeSchedule().getDayOfWeek(),
                        unit.getPracticeSchedule().getPeriodStart(), unit.getPracticeSchedule().getPeriodEnd(), startPractice, endPractice);
                checkPracticeSchedule = Stream.concat(checkPracticeSchedule1.stream(), checkPracticeSchedule.stream()).toList();
            }



        if(!Stream.concat(checkPracticeSchedule.stream(), checkSchedule.stream()).toList().isEmpty()){
            throw new IllegalStateException("class time overlaps");
        }

        if(unit.getSubject().getPrerequisite().isEmpty()){
            try{
                Enroll enroll  = new Enroll(student, unit, null);
                Enroll saved = enrollRepository.save(enroll);
//                kafkaTemplate.send("notificationTopic",
//                        new RegisterSuccessEvent(student.getEmail(), saved.getUnit().getSubject().getSubjectName(), unit.getTuitionPerCredit() * unit.getSubject().getCredit()));
                return true;
            }catch (OptimisticLockingFailureException ex){
                return false;
            }
        }else{
            throw new IllegalStateException("The subject has prerequisite subjects");
        }
    }
}
