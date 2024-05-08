package iuh.fit.se.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SubjectRequest {
    private String subjectName;
    private int theoryCredit;
    private int practiceCredit;
    private String description;
    private Set<Long> prerequisiteIds;
}
