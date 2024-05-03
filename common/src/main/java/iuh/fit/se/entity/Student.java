package iuh.fit.se.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String studentCode;
    private String fullName;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String phone;
    private String email;
    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;
    @ManyToOne
    @JoinColumn(name = "major_id")
    @Nullable
    private Major major;
    @ManyToOne
    @JoinColumn(name = "cohorts_id")
    private Cohorts cohorts;
    @OneToMany(mappedBy = "student")
    private Set<Enroll> registrations;
}
