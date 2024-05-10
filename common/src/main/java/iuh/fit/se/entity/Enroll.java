package iuh.fit.se.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@IdClass(EnrollId.class)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
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
    @JoinColumn(name = "result_id")
    private Result result;

}
