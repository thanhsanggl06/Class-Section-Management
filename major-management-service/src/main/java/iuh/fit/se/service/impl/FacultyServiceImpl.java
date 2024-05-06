package iuh.fit.se.service.impl;

import iuh.fit.se.dto.FacultyRequest;
import iuh.fit.se.entity.Faculty;
import iuh.fit.se.exception.ResourceNotFoundException;
import iuh.fit.se.mapper.FacultyAutoMapper;
import iuh.fit.se.repository.FacultyRepository;
import iuh.fit.se.service.FacultyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FacultyServiceImpl implements FacultyService {
    private FacultyRepository facultyRepository;
    @Override
    public Faculty findById(long id) {
        Faculty faculty = facultyRepository.findById(id).orElseThrow(() ->  new ResourceNotFoundException("FACULTY", "ID", id));
        return faculty;
    }

    @Override
    public Iterable<Faculty> getAll() {
        List<Faculty> list =  facultyRepository.findAll();
        return list;
    }

    @Override
    public Faculty create(FacultyRequest request) {
        Faculty faculty = FacultyAutoMapper.MAPPER.mapToFaculty(request);
        Faculty savedFaculty = facultyRepository.save(faculty);
        return savedFaculty;
    }

    @Override
    public Faculty update(long id, FacultyRequest request) {
        Faculty existing = facultyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("FACULTY", "ID", id));
        existing.setFacultyCode(request.getFacultyCode());
        existing.setName(request.getName());

        Faculty updatedFaculty = facultyRepository.save(existing);
        return updatedFaculty;
    }
}
