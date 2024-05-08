package iuh.fit.se.controller;

import iuh.fit.se.dto.CohortsRequest;
import iuh.fit.se.entity.Cohorts;
import iuh.fit.se.service.CohortsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cohorts")
@AllArgsConstructor
public class CohortsController {
    private final CohortsService cohortsService;

    @GetMapping("/{id}")
    public ResponseEntity<Cohorts> findById(@PathVariable long id){
        return ResponseEntity.ok(cohortsService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Iterable<Cohorts>> findAll(){
        return  ResponseEntity.ok(cohortsService.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<Cohorts> create(@RequestBody CohortsRequest cohortsRequest){
        return new ResponseEntity(cohortsService.create(cohortsRequest), HttpStatus.CREATED);
    }
}
