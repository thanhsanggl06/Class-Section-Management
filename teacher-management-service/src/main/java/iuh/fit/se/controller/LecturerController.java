package iuh.fit.se.controller;

import iuh.fit.se.dto.LecturerRequest;
import iuh.fit.se.dto.LecturerResponse;
import iuh.fit.se.service.LecturerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lecturer")
@AllArgsConstructor
public class LecturerController {
    private final LecturerService lecturerService;

    @GetMapping
    public ResponseEntity<Iterable<LecturerResponse>> getAll(){
        return ResponseEntity.ok(lecturerService.getAll()) ;
    }

    @GetMapping("/faculty/{id}")
    public ResponseEntity<Iterable<LecturerResponse>> getLecturersByFaculty(@PathVariable long id){
        return ResponseEntity.ok(lecturerService.getAllLecturersOfFaculty(id)) ;
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<LecturerResponse> findById(@PathVariable long id){
//        return ResponseEntity.ok(lecturerService.findById(id));
//    }

    @GetMapping("/{code}")
    public ResponseEntity<LecturerResponse> findByLecturerCode(@PathVariable String code){
        return ResponseEntity.ok(lecturerService.findByLecturerCode(code));
    }

    @PostMapping("/add")
    public ResponseEntity<LecturerResponse> add(@RequestBody LecturerRequest lecturerRequest){
        return new ResponseEntity(lecturerService.create(lecturerRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LecturerResponse> update(@PathVariable long id ,@RequestBody LecturerRequest lecturerRequest){
        return ResponseEntity.ok(lecturerService.update(id, lecturerRequest));
    }
}
