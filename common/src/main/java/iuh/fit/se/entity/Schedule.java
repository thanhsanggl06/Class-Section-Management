package iuh.fit.se.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Schedule {
    @Id
    private long id;
    private int dayOfWeek;
    private int periodStart;
    private int periodEnd;
}
