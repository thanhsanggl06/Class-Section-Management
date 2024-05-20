package iuh.fit.se.service.Impl;

import iuh.fit.se.constant.Constant;
import iuh.fit.se.dto.StudentRequest;
import iuh.fit.se.dto.StudentResponse;
import iuh.fit.se.entity.*;
import iuh.fit.se.exception.ResourceNotFoundException;
import iuh.fit.se.mapper.StudentAutoMapper;
import iuh.fit.se.repository.AccountRepository;
import iuh.fit.se.repository.StudentRepository;
import iuh.fit.se.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final StudentRepository studentRepository;
    private final WebClient.Builder webClientBuilder;
    @Override
    public StudentResponse findByStudentCode(String studentCode) {
        Student student = studentRepository.findByStudentCode(studentCode).orElseThrow(() -> new ResourceNotFoundException("STUDENT","CODE", studentCode));
        StudentResponse studentResponse = StudentAutoMapper.MAPPER.mapToStudentResponse(student);
        studentResponse.setFacultyId(student.getFaculty().getId());
        studentResponse.setCohortsId(student.getCohorts().getId());
        studentResponse.setMajorId(student.getCohorts().getId());
        return studentResponse;
    }

    @Override
    public Iterable<StudentResponse> getAllStudentsOfMajor(long majorId) {
        Major major = webClientBuilder.build().get()
                .uri("http://faculty-major-service/api/major/{id}", majorId)
                .retrieve()
                .bodyToMono(Major.class).block();

        if(major == null){
            throw new ResourceNotFoundException("MAJOR",  "ID", majorId);
        }
        List<StudentResponse> responseList = studentRepository.findAllByMajor(major).stream().map(student -> {
            StudentResponse response = StudentAutoMapper.MAPPER.mapToStudentResponse(student);
            response.setFacultyId(student.getFaculty().getId());
            response.setCohortsId(student.getCohorts().getId());
            response.setMajorId(major.getId());
            return response;
        }).collect(Collectors.toList());
        return responseList;
    }


    @Override
    @Transactional
    public StudentResponse create(StudentRequest request) {
        Student student = StudentAutoMapper.MAPPER.mapToStudent(request);

        long maxValue;
        Major major = webClientBuilder.build().get()
                .uri("http://faculty-major-service/api/major/{id}", request.getMajorId())
                .retrieve()
                .bodyToMono(Major.class).block();

        if(major == null){
            throw new ResourceNotFoundException("MAJOR",  "ID", request.getMajorId());
        }

        Faculty faculty = studentRepository.findFacultyByMajor(major.getId());


        Cohorts cohorts = webClientBuilder.build().get()
                .uri("http://course-service/api/cohorts/{id}", request.getCohortsId())
                .retrieve()
                .bodyToMono(Cohorts.class).block();

        if(cohorts == null){
            throw new ResourceNotFoundException("COHORTS",  "ID", request.getCohortsId());
        }

        //Generate studentCode
        Long maxId = studentRepository.findMaxId();
        if(maxId != null) {
            maxValue = maxId.longValue()+1;
        }else{
            maxValue = 1;
        }
        String studentCode = LocalDate.now().getYear()%100 + String.format("%06d", maxValue);

        student.setStudentCode(studentCode);
        student.setFaculty(faculty);
        student.setCohorts(cohorts);
        student.setMajor(major);
        Student savedStudent = studentRepository.save(student);

        Account account = new Account();
        account.linkToStudent(savedStudent);
        account.setPassword(passwordEncoder.encode(Constant.DEFAULT_PWD));
        accountRepository.save(account);

        StudentResponse response = StudentAutoMapper.MAPPER.mapToStudentResponse(savedStudent);
        response.setFacultyId(faculty.getId());
        response.setCohortsId(cohorts.getId());
        response.setMajorId(major.getId());
        return response;
    }

    @Override
    public StudentResponse update(long id, StudentRequest request) {
        return null;
    }
}
