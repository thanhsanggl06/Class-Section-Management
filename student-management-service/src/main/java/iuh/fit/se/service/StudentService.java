package iuh.fit.se.service;

import iuh.fit.se.dto.StudentRequest;
import iuh.fit.se.dto.StudentResponse;

public interface StudentService {
    StudentResponse findByStudentCode(String studentCode);
    Iterable<StudentResponse> getAllStudentsOfMajor(long majorId);
    StudentResponse create(StudentRequest request);
    StudentResponse update(long id, StudentRequest request);
}
