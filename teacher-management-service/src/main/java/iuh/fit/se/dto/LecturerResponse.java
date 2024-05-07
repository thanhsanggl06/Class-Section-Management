package iuh.fit.se.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LecturerResponse {
    private long id;
    private String fullName;
    private String lecturerCode;
    private long facultyId;
}
