package iuh.fit.se.mapper;

import iuh.fit.se.dto.CohortsRequest;
import iuh.fit.se.entity.Cohorts;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CohortsAutoMapper {
    CohortsAutoMapper MAPPER = Mappers.getMapper(CohortsAutoMapper.class);
    Cohorts mapToCohorts(CohortsRequest cohortsRequest);
}
