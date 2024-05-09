package iuh.fit.se.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
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
    @ManyToOne
    @JoinColumn(name = "practice_schedule_id")
    private Schedule practiceSchedule;
    @OneToMany(mappedBy = "unit")
    private Set<Enroll> registrations;
    @Enumerated(EnumType.STRING)
    private AcademicProgram academicProgram;
    private LocalDate startDate;
    private LocalDate endDate;
    private double tuitionPerCredit;

}
