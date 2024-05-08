package iuh.fit.se.controller;

import iuh.fit.se.dto.ClassRoomRequest;
import iuh.fit.se.dto.ClassRoomResponse;
import iuh.fit.se.service.ClassRoomService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/room")
@AllArgsConstructor
public class ClassRoomController {
    private final ClassRoomService classRoomService;

    @PostMapping("/add")
    public ResponseEntity<ClassRoomResponse> add(@RequestBody ClassRoomRequest classRoomRequest){
        return new ResponseEntity (classRoomService.add(classRoomRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Iterable<ClassRoomResponse>> findAll(){
        return ResponseEntity.ok(classRoomService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassRoomResponse> findById(@PathVariable long id){
        return ResponseEntity.ok(classRoomService.findById(id));
    }
}
