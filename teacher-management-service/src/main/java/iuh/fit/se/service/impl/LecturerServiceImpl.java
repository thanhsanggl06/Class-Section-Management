package iuh.fit.se.service.impl;

import iuh.fit.se.constant.Constant;
import iuh.fit.se.dto.LecturerRequest;
import iuh.fit.se.dto.LecturerResponse;
import iuh.fit.se.entity.Account;
import iuh.fit.se.entity.Faculty;
import iuh.fit.se.entity.Lecturer;
import iuh.fit.se.exception.ResourceNotFoundException;
import iuh.fit.se.mapper.LecturerAutoMapper;
import iuh.fit.se.repository.AccountRepository;
import iuh.fit.se.repository.LecturerRepository;
import iuh.fit.se.service.LecturerService;
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
public class LecturerServiceImpl implements LecturerService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final LecturerRepository lecturerRepository;
    private final WebClient.Builder webClientBuilder;
    @Override
    public LecturerResponse findById(long id) {
        Lecturer lecturer = lecturerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("LECTURER", "ID", id));
        LecturerResponse response = LecturerAutoMapper.MAPPER.mapToLecturerResponse(lecturer);
        response.setFacultyId(lecturer.getFaculty().getId());
        return response;
    }

    @Override
    public LecturerResponse findByLecturerCode(String lecturerCode) {
        Lecturer lecturer = lecturerRepository.findByLecturerCode(lecturerCode).orElseThrow(() -> new ResourceNotFoundException("LECTURER", "CODE", lecturerCode));
        LecturerResponse response = LecturerAutoMapper.MAPPER.mapToLecturerResponse(lecturer);
        response.setFacultyId(lecturer.getFaculty().getId());
        return response;
    }

    @Override
    public Iterable<LecturerResponse> getAllLecturersOfFaculty(long facultyId) {
        Faculty faculty = webClientBuilder.build().get()
                .uri("http://faculty-major-service/api/faculty/{id}", facultyId)
                .retrieve()
                .bodyToMono(Faculty.class).block();

        if(faculty == null){
            throw new ResourceNotFoundException("FACULTY",  "ID", facultyId);
        }
        List<LecturerResponse> responses = lecturerRepository.findAllByFaculty(faculty).stream().map( lecturer -> {
            LecturerResponse response = LecturerAutoMapper.MAPPER.mapToLecturerResponse(lecturer);
            response.setFacultyId(lecturer.getFaculty().getId());
            return  response;
        }).collect(Collectors.toList());
        return responses;
    }

    @Override
    public Iterable<LecturerResponse> getAll() {
        List<LecturerResponse> responses = lecturerRepository.findAll().stream().map( lecturer -> {
            LecturerResponse response = LecturerAutoMapper.MAPPER.mapToLecturerResponse(lecturer);
            response.setFacultyId(lecturer.getFaculty().getId());
            return  response;
        }).collect(Collectors.toList());
        return responses;
    }

    @Override
    @Transactional
    public LecturerResponse create(LecturerRequest request) {
        Lecturer lecturer = LecturerAutoMapper.MAPPER.mapToLecturer(request);
        long maxValue;

        Faculty faculty = webClientBuilder.build().get()
                .uri("http://faculty-major-service/api/faculty/{id}", request.getFacultyId())
                .retrieve()
                .bodyToMono(Faculty.class).block();

        if(faculty == null){
            throw new ResourceNotFoundException("FACULTY",  "ID", request.getFacultyId());
        }

        //Generate code
        Long maxId = lecturerRepository.findMaxId();
        if(maxId != null) {
            maxValue = maxId.longValue()+1;
        }else{
            maxValue = 1;
        }
        String lecturerCode = LocalDate.now().getYear()%100 + String.format("%05d", maxValue);

        lecturer.setLecturerCode(lecturerCode);
        lecturer.setFaculty(faculty);
        Lecturer savedLecture = lecturerRepository.save(lecturer);

        Account account = new Account();
        account.linkToLecturer(savedLecture);
        account.setPassword(passwordEncoder.encode(Constant.DEFAULT_PWD));
        accountRepository.save(account);

        LecturerResponse response = LecturerAutoMapper.MAPPER.mapToLecturerResponse(savedLecture);
        response.setFacultyId(faculty.getId());
        return response;
    }

    @Override
    public LecturerResponse update(long id, LecturerRequest request) {
        Lecturer existing = lecturerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("LECTURER", "ID", id));

        Faculty faculty = webClientBuilder.build().get()
                .uri("http://faculty-major-service/api/faculty/{id}", request.getFacultyId())
                .retrieve()
                .bodyToMono(Faculty.class).block();

        if(faculty == null){
            throw new ResourceNotFoundException("FACULTY",  "ID", request.getFacultyId());
        }
        existing.setFullName(request.getFullName());
        existing.setFaculty(faculty);

        Lecturer updatedLecturer = lecturerRepository.save(existing);

        LecturerResponse response = LecturerAutoMapper.MAPPER.mapToLecturerResponse(updatedLecturer);
        response.setFacultyId(faculty.getId());
        return response;
    }
}
