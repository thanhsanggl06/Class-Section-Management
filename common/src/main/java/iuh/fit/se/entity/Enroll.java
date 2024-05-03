package iuh.fit.se.entity;

import jakarta.persistence.*;

@Entity
public class Enroll {

    @Id
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
    @Id
    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @OneToOne
    @JoinColumn(name = "result_íd")
    private Result result;

}
