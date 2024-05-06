package iuh.fit.se.controller;

import iuh.fit.se.dto.MajorRequest;
import iuh.fit.se.dto.MajorResponse;
import iuh.fit.se.service.MajorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/major")
@AllArgsConstructor
public class MajorController {
    private final MajorService majorService;

    @GetMapping
    public ResponseEntity<Iterable<MajorResponse>> getAll(){
        return ResponseEntity.ok(majorService.getAll());
    }
    @GetMapping("/faculty/{id}")
    public ResponseEntity<Iterable<MajorResponse>> getMajorsOfFaculty(@PathVariable long id){
        return  ResponseEntity.ok(majorService.getAllMajorOfFaculty(id));
    }
    @GetMapping("/{id}")
    public ResponseEntity<MajorResponse> getMajorById(@PathVariable long id){
        return ResponseEntity.ok(majorService.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<MajorResponse> create(@RequestBody MajorRequest request){
        return new ResponseEntity(majorService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MajorResponse> getMajorById(@PathVariable long id, @RequestBody MajorRequest request){
        return ResponseEntity.ok(majorService.update(id, request));
    }
}
