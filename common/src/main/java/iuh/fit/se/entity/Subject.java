package iuh.fit.se.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Subject{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String subjectCode;
    private String subjectName;
    private int credit;
    private int theoryCredit;
    private int practiceCredit;

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
