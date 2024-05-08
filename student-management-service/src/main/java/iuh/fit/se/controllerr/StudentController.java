package iuh.fit.se.controllerr;

import iuh.fit.se.dto.StudentRequest;
import iuh.fit.se.dto.StudentResponse;
import iuh.fit.se.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
@AllArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/{code}")
    public ResponseEntity<StudentResponse> findByCode(@PathVariable String code){
        return  ResponseEntity.ok(studentService.findByStudentCode(code));
    }

    @GetMapping("/major/{id}")
    public ResponseEntity<Iterable<StudentResponse>> findStudentsByMajor(@PathVariable long id){
        return  ResponseEntity.ok(studentService.getAllStudentsOfMajor(id));
    }
    @PostMapping("/add")
    public ResponseEntity<StudentResponse> add(@RequestBody StudentRequest studentRequest){
        return new ResponseEntity(studentService.create(studentRequest), HttpStatus.CREATED);
    }
}
