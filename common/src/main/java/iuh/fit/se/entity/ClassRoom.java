package iuh.fit.se.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ClassRoom {
    @Id
    private long id;
    private String roomName;
}
