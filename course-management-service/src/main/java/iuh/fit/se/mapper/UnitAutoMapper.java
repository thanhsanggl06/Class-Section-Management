package iuh.fit.se.mapper;

import iuh.fit.se.dto.UnitRequest;
import iuh.fit.se.dto.UnitResponse;
import iuh.fit.se.entity.Unit;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UnitAutoMapper {
    UnitAutoMapper MAPPER = Mappers.getMapper(UnitAutoMapper.class);
    Unit mapToUnit(UnitRequest unitRequest);
    UnitResponse mapToUnitResponse(Unit unit);
}
