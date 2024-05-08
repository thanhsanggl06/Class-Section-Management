package iuh.fit.se.repository;

import iuh.fit.se.entity.Faculty;
import iuh.fit.se.entity.Major;
import iuh.fit.se.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByStudentCode(String studentCode);
    List<Student> findAllByMajor(Major major);
    @Query(value = "SELECT MAX(s.id) FROM Student s")
    Long findMaxId();
    @Query(value = "SELECT m.faculty  FROM Major m WHERE m.id = :id")
    Faculty findFacultyByMajor(@Param("id") Long majorId);
}
