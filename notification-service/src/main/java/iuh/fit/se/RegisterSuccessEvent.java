package iuh.fit.se;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterSuccessEvent {
    private String email;
    private String subjectName;
    private double tuition;
}
