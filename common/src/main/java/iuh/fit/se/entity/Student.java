package iuh.fit.se.entity;

import jakarta.annotation.Nullable;
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
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String studentCode;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8")
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
