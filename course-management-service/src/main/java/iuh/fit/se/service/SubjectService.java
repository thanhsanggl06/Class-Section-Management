package iuh.fit.se.service;

import iuh.fit.se.dto.SubjectRequest;
import iuh.fit.se.dto.SubjectResponse;

public interface SubjectService {
    SubjectResponse create(SubjectRequest subjectRequest);
    SubjectResponse update(long id ,SubjectRequest subjectRequest);
    SubjectResponse findById(long id);

}
