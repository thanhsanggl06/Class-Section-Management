package iuh.fit.se.controller;

import iuh.fit.se.dto.ScheduleRequest;
import iuh.fit.se.dto.ScheduleResponse;
import iuh.fit.se.service.ScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schedule")
@AllArgsConstructor
public class ScheduleController {
    private ScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<Iterable<ScheduleResponse>> findAll(){
        return ResponseEntity.ok(scheduleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponse> findById(@PathVariable long id){
        return ResponseEntity.ok(scheduleService.findById(id));
    }
    @PostMapping("/create")
    public ResponseEntity<ScheduleResponse> create(@RequestBody ScheduleRequest scheduleRequest){
        return new ResponseEntity(scheduleService.create(scheduleRequest), HttpStatus.CREATED);
    }
}
