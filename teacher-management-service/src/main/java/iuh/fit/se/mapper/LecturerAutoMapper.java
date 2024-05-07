package iuh.fit.se.mapper;

import iuh.fit.se.dto.LecturerRequest;
import iuh.fit.se.dto.LecturerResponse;
import iuh.fit.se.entity.Lecturer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LecturerAutoMapper {
    LecturerAutoMapper MAPPER = Mappers.getMapper(LecturerAutoMapper.class);
    LecturerResponse mapToLecturerResponse(Lecturer lecturer);
    Lecturer mapToLecturer(LecturerRequest request);
}
