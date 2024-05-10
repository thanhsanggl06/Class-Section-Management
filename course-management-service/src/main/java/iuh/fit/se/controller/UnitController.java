package iuh.fit.se.controller;

import iuh.fit.se.dto.UnitRequest;
import iuh.fit.se.dto.UnitResponse;
import iuh.fit.se.entity.Unit;
import iuh.fit.se.service.UnitService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/unit")
@AllArgsConstructor
public class UnitController {
    private final UnitService unitService;

    @GetMapping("/subject/{id}")
    public ResponseEntity<Iterable<UnitResponse>> findUnitsBySubjectId(@PathVariable long id){
        return ResponseEntity.ok(unitService.findUnitsBySubjectId(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Unit> findById(@PathVariable long id){
        return ResponseEntity.ok(unitService.findById(id));
    }



    @PostMapping("/create")
    public ResponseEntity<UnitResponse> create(@RequestBody UnitRequest unitRequest){
        return new ResponseEntity(unitService.create(unitRequest), HttpStatus.CREATED);
    }
}
