package iuh.fit.se.service;

import iuh.fit.se.dto.MajorRequest;
import iuh.fit.se.dto.MajorResponse;

public interface MajorService {
    MajorResponse findById(long id);
    Iterable<MajorResponse> getAllMajorOfFaculty(long facultyId);
    Iterable<MajorResponse> getAll();
    MajorResponse create(MajorRequest request);
    MajorResponse update(long id, MajorRequest request);
}
