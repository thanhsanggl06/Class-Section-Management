package iuh.fit.se.mapper;

import iuh.fit.se.dto.MajorRequest;
import iuh.fit.se.dto.MajorResponse;
import iuh.fit.se.entity.Major;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MajorAutoMapper {
    MajorAutoMapper MAPPER = Mappers.getMapper(MajorAutoMapper.class);
    Major mapToMajor (MajorRequest request);
    MajorResponse mapToMajorResponse(Major major);
}
