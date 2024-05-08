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
public class SubjectResponse {
    private long id;
    private String subjectName;
    private int credit;
    private int theoryCredit;
    private int practiceCredit;
    private String description;
    private Set<Long> prerequisiteIds;
}
