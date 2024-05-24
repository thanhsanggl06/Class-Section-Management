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
public class EducationProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8")
    private String name;
    @ManyToOne
    @JoinColumn(name = "major_id")
    private Major major;
    @ManyToOne
    @JoinColumn(name = "cohorts_id")
    private Cohorts cohorts;
    private int semester;
    @Enumerated(EnumType.STRING)
    private AcademicProgram academicProgram;

    @OneToMany(mappedBy = "educationProgram")
    private Set<EducationProgram_Subject> educationProgram_subjects;

}
