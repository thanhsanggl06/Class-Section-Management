package iuh.fit.se.repository;

import iuh.fit.se.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Schedule findByDayOfWeekAndPeriodStartAndPeriodEnd(int dayOfWeek, int periodStart, int periodEnd);
}
