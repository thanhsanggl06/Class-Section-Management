package iuh.fit.se.repository;

import iuh.fit.se.entity.ClassRoom;
import iuh.fit.se.entity.Lecturer;
import iuh.fit.se.entity.Subject;
import iuh.fit.se.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {
    List<Unit> findAllBySubject(Subject subject);
    @Query("SELECT count(u) From Unit u where u.classRoom = :classRoom AND u.schedule.dayOfWeek = :dayOfWeek AND((u.schedule.periodStart between :periodStart and :periodEnd) or (u.schedule.periodEnd between :periodStart and :periodEnd)) AND((u.startDate between :startDate and :endDate) or (u.endDate between :startDate and :endDate))")
    int countUnitsForClassRoomInTimeRange(@Param("classRoom") ClassRoom classRoom,
                                          @Param("dayOfWeek") int dayOfWeek,
                                          @Param("periodStart") int periodStart,
                                          @Param("periodEnd") int periodEnd,
                                          @Param("startDate") LocalDate startDate,
                                          @Param("endDate") LocalDate endDate);

    @Query("SELECT count(u) From Unit u where u.classRoom = :classRoom AND u.practiceSchedule.dayOfWeek = :dayOfWeek AND( (u.practiceSchedule.periodStart between :periodStart and :periodEnd) or (u.practiceSchedule.periodEnd between :periodStart and :periodEnd)) AND((u.startDate between :startDate and :endDate) or (u.endDate between :startDate and :endDate))")
    int countPracticeUnitsForClassRoomInTimeRange(@Param("classRoom") ClassRoom classRoom,
                                                  @Param("dayOfWeek") int dayOfWeek,
                                                  @Param("periodStart") int periodStart,
                                                  @Param("periodEnd") int periodEnd,
                                                  @Param("startDate") LocalDate startDate,
                                                  @Param("endDate") LocalDate endDate);

    @Query("SELECT count(u) From Unit u where u.lecturer = :lecturer AND u.schedule.dayOfWeek = :dayOfWeek AND( (u.schedule.periodStart between :periodStart and :periodEnd) or (u.schedule.periodEnd between :periodStart and :periodEnd)) AND((u.startDate between :startDate and :endDate) or (u.endDate between :startDate and :endDate))")
    int checkTheLecturerTheoreticalTeachingSchedule(@Param("lecturer") Lecturer lecturer,
                                                  @Param("dayOfWeek") int dayOfWeek,
                                                  @Param("periodStart") int periodStart,
                                                  @Param("periodEnd") int periodEnd,
                                                    @Param("startDate") LocalDate startDate,
                                                    @Param("endDate") LocalDate endDate
    );

    @Query("SELECT count(u) From Unit u where u.lecturer = :lecturer AND u.practiceSchedule.dayOfWeek = :dayOfWeek AND( (u.practiceSchedule.periodStart between :periodStart and :periodEnd) or (u.practiceSchedule.periodEnd between :periodStart and :periodEnd)) AND((u.startDate between :startDate and :endDate) or (u.endDate between :startDate and :endDate))")
    int checkTheLecturerPracticalTeachingSchedule(@Param("lecturer") Lecturer lecturer,
                                                    @Param("dayOfWeek") int dayOfWeek,
                                                    @Param("periodStart") int periodStart,
                                                    @Param("periodEnd") int periodEnd,
                                                  @Param("startDate") LocalDate startDate,
                                                  @Param("endDate") LocalDate endDate
    );
}
