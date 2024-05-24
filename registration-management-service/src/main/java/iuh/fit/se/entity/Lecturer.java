package iuh.fit.se.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Lecturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String lecturerCode;
    @Column(columnDefinition = "TEXT CHARACTER SET utf8")
    private String fullName;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

    public Lecturer(long id) {
        this.id = id;
    }
}
