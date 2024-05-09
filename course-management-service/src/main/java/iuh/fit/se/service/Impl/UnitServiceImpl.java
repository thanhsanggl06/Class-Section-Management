package iuh.fit.se.service.Impl;

import iuh.fit.se.constant.Constant;
import iuh.fit.se.dto.UnitRequest;
import iuh.fit.se.dto.UnitResponse;
import iuh.fit.se.entity.*;
import iuh.fit.se.exception.BadRequest;
import iuh.fit.se.exception.ResourceNotFoundException;
import iuh.fit.se.mapper.UnitAutoMapper;
import iuh.fit.se.repository.ClassRoomRepository;
import iuh.fit.se.repository.ScheduleRepository;
import iuh.fit.se.repository.SubjectRepository;
import iuh.fit.se.repository.UnitRepository;
import iuh.fit.se.service.UnitService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class UnitServiceImpl implements UnitService {
    private final UnitRepository unitRepository;
    private final ScheduleRepository scheduleRepository;
    private final SubjectRepository subjectRepository;
    private final ClassRoomRepository classRoomRepository;
    private final WebClient.Builder webClientBuilder;

    @Override
    public UnitResponse create(UnitRequest unitRequest) {
        Unit unit = UnitAutoMapper.MAPPER.mapToUnit(unitRequest);
        Subject subject = subjectRepository.findById(unitRequest.getSubjectId()).orElseThrow(() -> new ResourceNotFoundException("SUBJECT", "ID", unitRequest.getSubjectId()));
        ClassRoom classRoom = classRoomRepository.findById(unitRequest.getRoomId()).orElseThrow(() -> new ResourceNotFoundException("ROOM", "ID", unitRequest.getRoomId()));

        Lecturer lecturer = webClientBuilder.build().get()
                .uri("http://lecturer-service/api/lecturer/{code}", unitRequest.getLecturerCode())
                .retrieve()
                .bodyToMono(Lecturer.class).block();
        if (lecturer == null) {
            throw new ResourceNotFoundException("LECTURER", "CODE", unitRequest.getLecturerCode());
        }

        //Calculate endDate
        Schedule theory = scheduleRepository.findById(unitRequest.getScheduleId()).orElseThrow(() -> new ResourceNotFoundException("SCHEDULE", "ID", unitRequest.getScheduleId()));
        int periodPerLesson = theory.getPeriodEnd() - theory.getPeriodStart() + 1;
        int totalTheoryPeriod = Constant.PERIOD_PER_THEORY_CREDIT * subject.getTheoryCredit();
        int totalLesson = (int) Math.ceil(totalTheoryPeriod / periodPerLesson);
        LocalDate endTheory = unitRequest.getStartDate().plusWeeks(totalLesson);
        LocalDate endDate = endTheory.plusWeeks(1);

        //Check valid classroom
        int checkSchedule1 = unitRepository.countUnitsForClassRoomInTimeRange(classRoom, theory.getDayOfWeek(), theory.getPeriodStart(), theory.getPeriodEnd(), unitRequest.getStartDate(), endDate);
        int checkSchedule2 = unitRepository.countPracticeUnitsForClassRoomInTimeRange(classRoom, theory.getDayOfWeek(), theory.getPeriodStart(), theory.getPeriodEnd(),  unitRequest.getStartDate(), endDate);

        if(checkSchedule1 > 0 || checkSchedule2 > 0){
            throw new BadRequest("The classroom was used by another unit class");
        }

        //Check lecturer schedule
        int checkTheoreticalSchedule = unitRepository.checkTheLecturerTheoreticalTeachingSchedule(lecturer, theory.getDayOfWeek(), theory.getPeriodStart(), theory.getPeriodEnd(), unitRequest.getStartDate(), endDate);
        int checkPracticalSchedule = unitRepository.checkTheLecturerPracticalTeachingSchedule(lecturer, theory.getDayOfWeek(), theory.getPeriodStart(), theory.getPeriodEnd(), unitRequest.getStartDate(), endDate);
        if(checkTheoreticalSchedule > 0 || checkPracticalSchedule >0 ){
            throw new BadRequest("Lecturer's schedule clashed");
        }

        Schedule practice = null;
        if (subject.getPracticeCredit() > 0) {
            practice = scheduleRepository.findById(unitRequest.getPracticeScheduleId()).orElseThrow(() -> new ResourceNotFoundException("SCHEDULE FOR PRACTICE", "ID", unitRequest.getScheduleId()));
            unit.setPracticeSchedule(practice);

            //check valid schedule
            if(practice.getDayOfWeek() ==  theory.getDayOfWeek()){
                if((practice.getPeriodStart() >= theory.getPeriodStart() && practice.getPeriodStart() <=theory.getPeriodEnd()) ||
                        (practice.getPeriodEnd() >= theory.getPeriodStart() && practice.getPeriodEnd() <=theory.getPeriodEnd())
                ){
                    throw new BadRequest("The practical class schedule must not overlap with the theory class schedule");
                }
            }



            int periodPerPracticeLesson = practice.getPeriodEnd() - practice.getPeriodStart() + 1;
            int totalPracticePeriod = Constant.PERIOD_PER_PRACTICE_CREDIT * subject.getPracticeCredit();
            int totalPracticeLesson = (int) Math.ceil(totalPracticePeriod / periodPerPracticeLesson);

            LocalDate endPractice = unitRequest.getStartDate().plusWeeks(3+totalPracticeLesson+1);
            //Check lecturer schedule
            int checkTheoreticalSchedule2 = unitRepository.checkTheLecturerPracticalTeachingSchedule(lecturer, practice.getDayOfWeek(), practice.getDayOfWeek(), practice.getDayOfWeek(), unitRequest.getStartDate().plusWeeks(3), endPractice );
            int checkPracticalSchedule2 = unitRepository.checkTheLecturerPracticalTeachingSchedule(lecturer, practice.getDayOfWeek(), practice.getDayOfWeek(), practice.getDayOfWeek(), unitRequest.getStartDate().plusWeeks(3), endPractice );
            if(checkPracticalSchedule2 > 0 || checkTheoreticalSchedule2 >0 ){
                throw new BadRequest("Lecturer's schedule clashed");
            }
            if (endPractice.isAfter(endTheory))
                endDate = endPractice;
            int checkPracticeSchedule1 = unitRepository.countUnitsForClassRoomInTimeRange(classRoom, practice.getDayOfWeek(), practice.getPeriodStart(), practice.getPeriodEnd(), unitRequest.getStartDate().plusWeeks(3), endPractice);
            int checkPracticeSchedule2 = unitRepository.countPracticeUnitsForClassRoomInTimeRange(classRoom, practice.getDayOfWeek(), practice.getPeriodStart(), practice.getPeriodEnd(), unitRequest.getStartDate().plusWeeks(3), endPractice);
            if(checkPracticeSchedule1 > 0 || checkPracticeSchedule2 > 0){
                throw new BadRequest("The classroom was used by another unit class");
            }

            unit.setPracticeSchedule(practice);
        }

        unit.setLecturer(lecturer);
        unit.setClassRoom(classRoom);
        unit.setSubject(subject);
        unit.setSchedule(theory);
        unit.setEndDate(endDate);

        Unit savedUnit = unitRepository.save(unit);
        UnitResponse unitResponse = UnitAutoMapper.MAPPER.mapToUnitResponse(savedUnit);
        unitResponse.setLecturerId(lecturer.getId());
        unitResponse.setRoomId(classRoom.getId());
        unitResponse.setScheduleId(theory.getId());
        unitResponse.setSubjectId(subject.getId());
        if(practice != null)
            unitResponse.setPracticeScheduleId(practice.getId());

        return unitResponse;
    }
}
