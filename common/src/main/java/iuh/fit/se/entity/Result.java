package iuh.fit.se.entity;

import jakarta.persistence.*;

@Entity
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private float regular;
    private float midterm;
    @Column(name = "final")
    private float fjnal;
}
