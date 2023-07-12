package ua.foxminded.pskn.universitycms.model.university;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    @Column(name = "group_id")
    private int groupId;

    @Column(name = "professor_id")
    private int professorId;

    @Column(name = "course_id")
    private int courseId;

    @Column(name = "start_time")
    private Timestamp startTime;

    @Column(name = "end_time")
    private Timestamp endTime;

    @Column(name = "date")
    private Date date;

}
