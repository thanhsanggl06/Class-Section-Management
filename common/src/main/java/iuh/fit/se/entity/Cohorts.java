package iuh.fit.se.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Cohorts {
    @Id
    private long id;
    private String name;
    private int yearOfAdmission;
}
