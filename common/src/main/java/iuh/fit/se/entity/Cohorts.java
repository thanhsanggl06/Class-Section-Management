package iuh.fit.se.entity;

import jakarta.persistence.Column;
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
public class Cohorts {
    @Id
    private long id;
    @Column(unique = true)
    private String name;
    private int yearOfAdmission;
}
