package iuh.fit.se.repository;

import iuh.fit.se.entity.Faculty;
import iuh.fit.se.entity.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MajorRepository extends JpaRepository<Major, Long> {
    List<Major> findAllByFaculty(Faculty faculty);

}
