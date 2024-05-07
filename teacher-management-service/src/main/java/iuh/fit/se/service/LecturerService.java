package iuh.fit.se.service;

import iuh.fit.se.dto.LecturerRequest;
import iuh.fit.se.dto.LecturerResponse;

public interface LecturerService {
    LecturerResponse findById(long id);
    LecturerResponse findByLecturerCode(String lecturerCode);
    Iterable<LecturerResponse> getAllLecturersOfFaculty(long facultyId);
    Iterable<LecturerResponse> getAll();
    LecturerResponse create(LecturerRequest request);
    LecturerResponse update(long id, LecturerRequest request);
}
