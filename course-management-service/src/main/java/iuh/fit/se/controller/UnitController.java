package iuh.fit.se.controller;

import iuh.fit.se.dto.UnitRequest;
import iuh.fit.se.dto.UnitResponse;
import iuh.fit.se.service.UnitService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/unit")
@AllArgsConstructor
public class UnitController {
    private final UnitService unitService;

    @PostMapping("/create")
    public ResponseEntity<UnitResponse> create(@RequestBody UnitRequest unitRequest){
        return new ResponseEntity(unitService.create(unitRequest), HttpStatus.CREATED);
    }
}
