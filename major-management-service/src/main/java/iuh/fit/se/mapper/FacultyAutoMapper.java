package iuh.fit.se.mapper;

import iuh.fit.se.dto.FacultyRequest;
import iuh.fit.se.entity.Faculty;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FacultyAutoMapper {
    FacultyAutoMapper MAPPER = Mappers.getMapper(FacultyAutoMapper.class);
    Faculty mapToFaculty (FacultyRequest facultyRequest);

}
