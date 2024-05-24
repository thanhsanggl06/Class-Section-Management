package iuh.fit.se.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

@Getter
@Setter
public class RegisterSuccessEvent extends ApplicationEvent {
    private String email;
    private String subjectName;
    private double tuition;

    public RegisterSuccessEvent(Object source, String email, String subjectName, double tuition) {
        super(source);
        this.email = email;
        this.subjectName = subjectName;
        this.tuition = tuition;
    }

    public RegisterSuccessEvent(String email, String subjectName, double tuition) {
        super(UUID.randomUUID().toString());
        this.email = email;
        this.subjectName = subjectName;
        this.tuition = tuition;
    }
}
