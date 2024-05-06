package iuh.fit.se.service.impl;

import iuh.fit.se.dto.MajorRequest;
import iuh.fit.se.dto.MajorResponse;
import iuh.fit.se.entity.Faculty;
import iuh.fit.se.entity.Major;
import iuh.fit.se.exception.ResourceNotFoundException;
import iuh.fit.se.mapper.MajorAutoMapper;
import iuh.fit.se.repository.FacultyRepository;
import iuh.fit.se.repository.MajorRepository;
import iuh.fit.se.service.MajorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MajorServiceImpl implements MajorService {
    private final MajorRepository majorRepository;
    private final FacultyRepository facultyRepository;
    @Override
    public MajorResponse findById(long id) {
        Major major = majorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("MAJOR", "ID", id));
        MajorResponse response = MajorAutoMapper.MAPPER.mapToMajorResponse(major);
        response.setFaculty_id(major.getFaculty().getId());
        return response;
    }

    @Override
    public Iterable<MajorResponse> getAllMajorOfFaculty(long facultyId) {
        Faculty faculty = facultyRepository.findById(facultyId).orElseThrow(() -> new ResourceNotFoundException("FACULTY", "ID", facultyId));
        List<MajorResponse> responses = majorRepository.findAllByFaculty(faculty).stream().map( major -> {
            MajorResponse response = MajorAutoMapper.MAPPER.mapToMajorResponse(major);
            response.setFaculty_id(faculty.getId());
            return response;
        }).collect(Collectors.toList());
        return responses;
    }

    @Override
    public Iterable<MajorResponse> getAll() {
        List<MajorResponse> responses = majorRepository.findAll().stream().map( major -> {
           MajorResponse response = MajorAutoMapper.MAPPER.mapToMajorResponse(major);
           response.setFaculty_id(major.getFaculty().getId());
           return response;
        }).collect(Collectors.toList());
        return responses;
    }

    @Override
    public MajorResponse create(MajorRequest request) {
        Major major = MajorAutoMapper.MAPPER.mapToMajor(request);
        Faculty faculty = facultyRepository.findById(request.getFaculty_id()).orElseThrow(() -> new ResourceNotFoundException("FACULTY", "ID", request.getFaculty_id()));
        major.setFaculty(faculty);
        Major savedMajor = majorRepository.save(major);
        MajorResponse response = MajorAutoMapper.MAPPER.mapToMajorResponse(savedMajor);
        response.setFaculty_id(request.getFaculty_id());
        return response;
    }

    @Override
    public MajorResponse update(long id, MajorRequest request) {
        Major existing = majorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("MAJOR", "ID", id));
        Faculty faculty = facultyRepository.findById(request.getFaculty_id()).orElseThrow(() -> new ResourceNotFoundException("FACULTY", "ID", request.getFaculty_id()));
        existing.setFaculty(faculty);
        existing.setName(request.getName());

        Major updatedMajor = majorRepository.save(existing);
        MajorResponse response = MajorAutoMapper.MAPPER.mapToMajorResponse(updatedMajor);
        response.setFaculty_id(request.getFaculty_id());
        return response;
    }
}
