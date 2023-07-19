package ua.foxminded.pskn.universitycms.repository.university;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.foxminded.pskn.universitycms.model.university.Schedule;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class ScheduleRepositoryTest {

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
        .withDatabaseName("test")
        .withUsername("test")
        .withPassword("test");

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Test
    void shouldFindScheduleByGroupId() {
        int groupId = 1;
        Schedule schedule = Schedule.builder()
            .groupId(groupId)
            .courseId(1)
            .startTime(Timestamp.valueOf("2022-07-01 12:00:00"))
            .endTime(Timestamp.valueOf("2022-07-01 12:00:00"))
            .date(new Date(2022 - 1900, 6, 1))
            .build();
        scheduleRepository.save(schedule);

        List<Schedule> schedules = scheduleRepository.findScheduleByGroupId((long) groupId);

        assertEquals(4, schedules.size());
        assertEquals(groupId, schedules.get(0).getGroupId());
    }

    @Test
    void shouldFindScheduleByProfessorId() {
        int groupId = 1;
        int professorId = 1;
        Schedule schedule = Schedule.builder()
            .groupId(groupId)
            .courseId(1)
            .professorId(professorId)
            .startTime(Timestamp.valueOf("2022-07-01 12:00:00"))
            .endTime(Timestamp.valueOf("2022-07-01 12:00:00"))
            .date(new Date(2022 - 1900, 6, 1))
            .build();
        scheduleRepository.save(schedule);

        List<Schedule> schedules = scheduleRepository.findScheduleByProfessorId(professorId);

        assertEquals(3, schedules.size());
        assertEquals(professorId, schedules.get(0).getProfessorId());
    }

}

