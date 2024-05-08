package iuh.fit.se.dto;

import iuh.fit.se.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponse {
    private long id;
    private String studentCode;
    private String fullName;
    private Gender gender;
    private String phone;
    private String email;
    private long facultyId;
    private long majorId;
    private long cohortsId;
}
