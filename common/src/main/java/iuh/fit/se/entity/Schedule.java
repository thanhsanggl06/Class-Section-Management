package iuh.fit.se.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Schedule {
    @Id
    private long id;
    private int dayOfWeek;
    private int periodStart;
    private int periodEnd;
}
