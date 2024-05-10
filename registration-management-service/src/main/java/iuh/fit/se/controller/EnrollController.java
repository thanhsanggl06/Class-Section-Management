package iuh.fit.se.controller;

import iuh.fit.se.dto.EnrollRequest;
import iuh.fit.se.service.EnrollService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/enroll")
@AllArgsConstructor
public class EnrollController {
    private final EnrollService enrollService;

    @PostMapping("/register")
    public ResponseEntity<Boolean> register(@RequestBody EnrollRequest enrollRequest){
        return ResponseEntity.ok(enrollService.register(enrollRequest));
    }
}
