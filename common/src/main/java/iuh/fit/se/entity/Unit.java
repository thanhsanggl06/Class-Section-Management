package iuh.fit.se.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private ClassRoom classRoom;
    @ManyToOne
    @JoinColumn(name = "lecturer_id")
    private Lecturer lecturer;
    private int  numberOfStudent;
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;
    @OneToMany(mappedBy = "unit")
    private Set<Enroll> registrations;
    @Enumerated(EnumType.STRING)
    private AcademicProgram academicProgram;
    private LocalDate startDay;
    private LocalDate endDay;
    private double tuitionPerCredit;

}
