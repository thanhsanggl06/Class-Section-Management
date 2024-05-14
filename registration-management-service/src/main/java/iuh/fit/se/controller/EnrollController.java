package iuh.fit.se.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import iuh.fit.se.dto.EnrollRequest;
import iuh.fit.se.service.EnrollService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@RestController
@RequestMapping("/api/enroll")
@AllArgsConstructor
public class EnrollController {
    private final EnrollService enrollService;

    @PostMapping("/register")
    @CircuitBreaker(name = "student", fallbackMethod = "fallbackMethod")
    public ResponseEntity<?> register(@RequestBody EnrollRequest enrollRequest){
        return ResponseEntity.ok(enrollService.register(enrollRequest));
    }

    public ResponseEntity<?> fallbackMethod( EnrollRequest enrollRequest, WebClientResponseException e){
        return ResponseEntity.ok(false);
    }
}
