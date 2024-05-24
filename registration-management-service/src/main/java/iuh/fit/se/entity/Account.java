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
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String username; // studentCode or lecturerCode used for login

    private String password; // you should handle password securely, this is just for demonstration

    @Enumerated(EnumType.STRING)
    private Role role; // Assuming you have a Role enum defined somewhere, representing roles like STUDENT, LECTURER, etc.

    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student; // reference to the Student entitys

    @OneToOne
    @JoinColumn(name = "lecturer_id")
    private Lecturer lecturer; // reference to the Lecturer entity

    public void linkToStudent(Student student) {
        this.student = student;
        this.username = student.getStudentCode();
        this.role = Role.STUDENT; // assuming you have a Role enum defined
    }

    public void linkToLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
        this.username = lecturer.getLecturerCode();
        this.role = Role.LECTURER; // assuming you have a Role enum defined
    }

}
