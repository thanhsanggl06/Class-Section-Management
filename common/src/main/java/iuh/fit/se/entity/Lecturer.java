package iuh.fit.se.entity;

import jakarta.persistence.*;

@Entity
public class Lecturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String fullName;
    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;
}
