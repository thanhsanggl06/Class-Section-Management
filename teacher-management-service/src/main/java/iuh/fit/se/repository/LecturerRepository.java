package iuh.fit.se.repository;

import iuh.fit.se.entity.Faculty;
import iuh.fit.se.entity.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer, Long> {
    List<Lecturer> findAllByFaculty(Faculty faculty);
    Optional<Lecturer> findByLecturerCode(String lecturer_code);
    @Query(value = "SELECT MAX(l.id) FROM Lecturer l")
    Long findMaxId();
}
