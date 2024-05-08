package iuh.fit.se.repository;

import iuh.fit.se.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    @Query(value = "SELECT MAX(s.id) FROM Subject s")
    Long findMaxId();
}
