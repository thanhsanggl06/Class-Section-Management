package iuh.fit.se.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Subject{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8", unique = true)
    private String subjectName;
    private int credit;
    private int theoryCredit;
    private int practiceCredit;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8")
    private String description;
    @ManyToMany
    @JoinTable(
            name = "subject_prerequisite",
            joinColumns = @JoinColumn(name = "subject_id"),
            inverseJoinColumns = @JoinColumn(name = "prerequisite_id")
    )
    private Set<Subject> prerequisite;

    @OneToMany(mappedBy = "subject")
    private Set<EducationProgram_Subject> educationProgram_subjects;
}
