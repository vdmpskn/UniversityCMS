package ua.foxminded.pskn.universitycms.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDTO {

    private Long scheduleId;

    private Long groupId;

    private Long professorId;

    private Long courseId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private LocalDate date;
}
