package iuh.fit.se.controller;

import iuh.fit.se.dto.FacultyRequest;
import iuh.fit.se.entity.Faculty;
import iuh.fit.se.service.FacultyService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/faculty")
@AllArgsConstructor
public class FacultyController {
    private final FacultyService facultyService;

    @GetMapping
    public ResponseEntity<Iterable<Faculty>> getAll(){
        return ResponseEntity.ok(facultyService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getById(@PathVariable long id){
        return ResponseEntity.ok(facultyService.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Faculty> create(@RequestBody FacultyRequest facultyRequest){
        return new ResponseEntity(facultyService.create(facultyRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Faculty> update(@PathVariable long id, @RequestBody FacultyRequest facultyRequest){
        return ResponseEntity.ok(facultyService.update(id, facultyRequest));
    }
}
