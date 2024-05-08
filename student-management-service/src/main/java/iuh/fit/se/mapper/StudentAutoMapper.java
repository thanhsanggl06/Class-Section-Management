package iuh.fit.se.mapper;

import iuh.fit.se.dto.StudentRequest;
import iuh.fit.se.dto.StudentResponse;
import iuh.fit.se.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StudentAutoMapper {
    StudentAutoMapper MAPPER = Mappers.getMapper(StudentAutoMapper.class);

    Student mapToStudent(StudentRequest studentRequest);
    StudentResponse mapToStudentResponse(Student student);
}
