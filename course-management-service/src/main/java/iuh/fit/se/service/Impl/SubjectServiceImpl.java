package iuh.fit.se.service.Impl;

import iuh.fit.se.dto.SubjectRequest;
import iuh.fit.se.dto.SubjectResponse;
import iuh.fit.se.entity.Subject;
import iuh.fit.se.exception.ResourceNotFoundException;
import iuh.fit.se.mapper.SubjectAutoMapper;
import iuh.fit.se.repository.SubjectRepository;
import iuh.fit.se.service.SubjectService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository  subjectRepository;

    @Override
    public SubjectResponse create(SubjectRequest subjectRequest) {
        Subject subject = SubjectAutoMapper.MAPPER.mapToSubject(subjectRequest);


        Set<Subject> prerequisite = new HashSet<>();

        if(subjectRequest.getPrerequisiteIds() != null) {
           prerequisite = subjectRequest.getPrerequisiteIds().stream().map(id -> {
                Subject subject1 = subjectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("SUBJECT", "ID", id));
                return subject1;
            }).collect(Collectors.toSet());
        }

        subject.setCredit(subjectRequest.getTheoryCredit() + subjectRequest.getPracticeCredit());
        subject.setPrerequisite(prerequisite);

        Subject savedSubject = subjectRepository.save(subject);

        SubjectResponse subjectResponse = SubjectAutoMapper.MAPPER.mapToSubjectResponse(savedSubject);
        subjectResponse.setPrerequisiteIds(subjectRequest.getPrerequisiteIds());

        return subjectResponse;
    }

    @Override
    public SubjectResponse update(long id, SubjectRequest subjectRequest) {
        return null;
    }

    @Override
    public SubjectResponse findById(long id) {
        Subject subject = subjectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("SUBJECT", "ID", id));
        SubjectResponse subjectResponse = SubjectAutoMapper.MAPPER.mapToSubjectResponse(subject);
        subjectResponse.setPrerequisiteIds(subject.getPrerequisite().stream().map( subject1 -> subject1.getId()).collect(Collectors.toSet()));
        return subjectResponse;
    }
}
