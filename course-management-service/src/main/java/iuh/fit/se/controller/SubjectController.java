package iuh.fit.se.controller;

import iuh.fit.se.dto.SubjectRequest;
import iuh.fit.se.dto.SubjectResponse;
import iuh.fit.se.service.SubjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subject")
@AllArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;

    @GetMapping("/{id}")
    public  ResponseEntity<SubjectResponse> findById(@PathVariable long id){
        return ResponseEntity.ok(subjectService.findById(id));
    }
    @PostMapping("/create")
    public ResponseEntity<SubjectResponse> create(@RequestBody SubjectRequest subjectRequest){
        return new ResponseEntity(subjectService.create(subjectRequest), HttpStatus.CREATED);
    }
}
