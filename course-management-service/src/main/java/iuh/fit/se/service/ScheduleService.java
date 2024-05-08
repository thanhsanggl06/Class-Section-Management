package iuh.fit.se.service;

import iuh.fit.se.dto.ScheduleRequest;
import iuh.fit.se.dto.ScheduleResponse;

public interface ScheduleService {
    ScheduleResponse create(ScheduleRequest scheduleRequest);
    ScheduleResponse findById(long Id);
    Iterable<ScheduleResponse> findAll();
}
