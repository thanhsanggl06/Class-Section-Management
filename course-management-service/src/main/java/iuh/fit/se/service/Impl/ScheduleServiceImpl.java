package iuh.fit.se.service.Impl;

import iuh.fit.se.dto.ScheduleRequest;
import iuh.fit.se.dto.ScheduleResponse;
import iuh.fit.se.entity.Schedule;
import iuh.fit.se.exception.BadRequest;
import iuh.fit.se.exception.ResourceNotFoundException;
import iuh.fit.se.mapper.ScheduleAutoMapper;
import iuh.fit.se.repository.ScheduleRepository;
import iuh.fit.se.service.ScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;

    @Override
    public ScheduleResponse create(ScheduleRequest scheduleRequest) {
        Schedule schedule = ScheduleAutoMapper.MAPPER.mapToSchedule(scheduleRequest);
        if(scheduleRequest.getDayOfWeek() <0 || scheduleRequest.getDayOfWeek()>6 || scheduleRequest.getPeriodStart() <=0
            || scheduleRequest.getPeriodStart() >14 || scheduleRequest.getPeriodEnd() <=1 || scheduleRequest.getPeriodEnd() >15
                || scheduleRequest.getPeriodEnd() - scheduleRequest.getPeriodStart() <=0
        )
            throw new BadRequest("Schedule");
        Schedule checkExist = scheduleRepository.findByDayOfWeekAndPeriodStartAndPeriodEnd(schedule.getDayOfWeek(), schedule.getPeriodStart(), scheduleRequest.getPeriodEnd());

        Schedule saved;
        if(checkExist == null){
            saved = scheduleRepository.save(schedule);
        }else{
            throw new RuntimeException("Schedule existing");
        }


        ScheduleResponse scheduleResponse = ScheduleAutoMapper.MAPPER.mapToScheduleResponse(saved);
        return scheduleResponse;
    }

    @Override
    public ScheduleResponse findById(long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("SCHEDULE","ID",id));
        ScheduleResponse scheduleResponse = ScheduleAutoMapper.MAPPER.mapToScheduleResponse(schedule);
        return scheduleResponse;
    }

    @Override
    public Iterable<ScheduleResponse> findAll() {
        List<ScheduleResponse> scheduleResponses = scheduleRepository.findAll()
                .stream().map(ScheduleAutoMapper.MAPPER::mapToScheduleResponse).toList();
        return scheduleResponses;
    }
}
