package iuh.fit.se.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LecturerRequest {
    @NotEmpty
    private String fullName;
    @NotEmpty
    private long facultyId;
}
