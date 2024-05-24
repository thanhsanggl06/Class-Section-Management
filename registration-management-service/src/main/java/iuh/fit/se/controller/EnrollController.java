package iuh.fit.se.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import iuh.fit.se.dto.EnrollRequest;
import iuh.fit.se.service.EnrollService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/enroll")
@AllArgsConstructor
public class EnrollController {
    private final EnrollService enrollService;

    @PostMapping("/register")
    @CircuitBreaker(name = "student", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "student")
    @Retry(name = "student")
    public CompletableFuture<ResponseEntity<?>> register(@RequestBody EnrollRequest enrollRequest){
        Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.info("Register method started");
        return CompletableFuture.supplyAsync(() -> {
            try {
                Object rs = enrollService.register(enrollRequest);
                if (rs instanceof Boolean) {
                    return new ResponseEntity<>(rs, (Boolean) rs ? HttpStatus.CREATED : HttpStatus.CONFLICT);
                } else {
                    return ResponseEntity.ok(rs);
                }
            }catch (Exception e){
                logger.error("Exception in register method", e);
                throw e;
            }
        });
    }
//
    public CompletableFuture<ResponseEntity<?>> fallbackMethod(EnrollRequest enrollRequest, RuntimeException e){
        Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.error("Fallback method called due to: ", e);
        return CompletableFuture.supplyAsync(() -> new ResponseEntity<>("The server has a problem. Please try again later!", HttpStatus.INTERNAL_SERVER_ERROR));
    }

//    @PostMapping("/register")
//    @CircuitBreaker(name = "student", fallbackMethod = "fallbackMethod")
//    public ResponseEntity<?> register(@RequestBody EnrollRequest enrollRequest){
//            Object rs = enrollService.register(enrollRequest);
//            if (rs instanceof Boolean) {
//                return new ResponseEntity<>(rs, (Boolean) rs ? HttpStatus.CREATED : HttpStatus.CONFLICT);
//            } else {
//                return ResponseEntity.ok(rs);
//            }
//    }
//
//    public ResponseEntity<?> fallbackMethod(EnrollRequest enrollRequest, RuntimeException e){
//        return  new ResponseEntity<>("The server has a problem. Please try again later!", HttpStatus.INTERNAL_SERVER_ERROR);
//    }


}
