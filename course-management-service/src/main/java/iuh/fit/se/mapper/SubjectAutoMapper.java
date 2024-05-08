package iuh.fit.se.mapper;

import iuh.fit.se.dto.SubjectRequest;
import iuh.fit.se.dto.SubjectResponse;
import iuh.fit.se.entity.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SubjectAutoMapper {
    SubjectAutoMapper MAPPER = Mappers.getMapper(SubjectAutoMapper.class);
    Subject mapToSubject(SubjectRequest subjectRequest);

    SubjectResponse mapToSubjectResponse(Subject subject);
}
