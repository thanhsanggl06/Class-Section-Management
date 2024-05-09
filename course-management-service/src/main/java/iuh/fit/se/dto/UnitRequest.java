package iuh.fit.se.dto;

import iuh.fit.se.entity.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UnitRequest {
    private long subjectId;
    private long roomId;
    private long lecturerCode;
    private int  numberOfStudent;
    private long scheduleId;
    private long practiceScheduleId;
    private AcademicProgram academicProgram;
    private LocalDate startDate;
    private double tuitionPerCredit;
}
