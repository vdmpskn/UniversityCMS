package ua.foxminded.pskn.universitycms.dto;

import java.sql.Date;
import java.sql.Timestamp;

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

    private int groupId;

    private int professorId;

    private int courseId;

    private Timestamp startTime;

    private Timestamp endTime;

    private Date date;
}
