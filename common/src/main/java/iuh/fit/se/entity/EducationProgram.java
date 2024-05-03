package iuh.fit.se.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class EducationProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
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
