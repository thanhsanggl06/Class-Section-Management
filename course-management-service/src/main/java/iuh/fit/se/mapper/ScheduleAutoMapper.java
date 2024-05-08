package iuh.fit.se.mapper;

import iuh.fit.se.dto.ScheduleRequest;
import iuh.fit.se.dto.ScheduleResponse;
import iuh.fit.se.entity.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ScheduleAutoMapper {
    ScheduleAutoMapper MAPPER = Mappers.getMapper(ScheduleAutoMapper.class);
    Schedule mapToSchedule(ScheduleRequest scheduleRequest);
    ScheduleResponse mapToScheduleResponse(Schedule schedule);
}
