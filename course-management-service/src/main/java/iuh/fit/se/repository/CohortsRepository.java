package iuh.fit.se.repository;

import iuh.fit.se.entity.Cohorts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CohortsRepository extends JpaRepository<Cohorts, Long> {
}
