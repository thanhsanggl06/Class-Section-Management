package iuh.fit.se.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequest extends RuntimeException{
    private String resourceName;
    public BadRequest(String resourceName) {
        super(String.format("%s request invalid", resourceName));
        this.resourceName = resourceName;
    }
}
