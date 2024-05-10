package iuh.fit.se.repository;

import iuh.fit.se.entity.Enroll;
import iuh.fit.se.entity.EnrollId;
import iuh.fit.se.entity.Student;
import iuh.fit.se.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EnrollRepository extends JpaRepository<Enroll, EnrollId> {
    @Query("SELECT COUNT(e) FROM Enroll e WHERE e.unit = :unit")
    int countEnrollmentsByUnit(@Param("unit") Unit unit);

    @Query("SELECT u.id FROM Unit u " +
            "JOIN Enroll e ON u.id = e.unit.id " +
            "JOIN e.student stu " +
            "JOIN u.schedule s " +
            "WHERE stu = :student " +
            "AND s.dayOfWeek = :dayOfWeek " +
            "AND ((s.periodStart BETWEEN :periodStart AND :periodEnd) OR (s.periodEnd BETWEEN :periodStart AND :periodEnd))" +
            "AND((u.startDate between :startDate and :endDate) or (u.endDate between :startDate and :endDate))")
    List<Long> selectUnitIdsByStudentAndSchedule(@Param("student")Student student,
                                                 @Param("dayOfWeek") int dayOfWeek,
                                                 @Param("periodStart") int periodStart,
                                                 @Param("periodEnd") int periodEnd,
                                                 @Param("startDate") LocalDate startDate,
                                                 @Param("endDate") LocalDate endDate
    );

    @Query("SELECT u.id FROM Unit u " +
            "JOIN Enroll e ON u.id = e.unit.id " +
            "JOIN e.student stu " +
            "JOIN u.practiceSchedule s " +
            "WHERE stu = :student " +
            "AND s.dayOfWeek = :dayOfWeek " +
            "AND ((s.periodStart BETWEEN :periodStart AND :periodEnd) OR (s.periodEnd BETWEEN :periodStart AND :periodEnd))" +
            "AND((u.startDate between :startDate and :endDate) or (u.endDate between :startDate and :endDate))")
    List<Long> selectUnitIdsByStudentAndPracticeSchedule(@Param("student")Student student,
                                                 @Param("dayOfWeek") int dayOfWeek,
                                                 @Param("periodStart") int periodStart,
                                                 @Param("periodEnd") int periodEnd,
                                                 @Param("startDate") LocalDate startDate,
                                                 @Param("endDate") LocalDate endDate
    );
}
