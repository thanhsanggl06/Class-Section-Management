package iuh.fit.se.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MajorResponse {
    private long id;
    private String name;
    private long faculty_id;
}
