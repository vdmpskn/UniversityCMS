package ua.foxminded.pskn.universitycms.repository.university;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import ua.foxminded.pskn.universitycms.model.university.Schedule;

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

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void shouldFindScheduleByGroupId() {
        Long groupId = 1L;
        Schedule schedule = Schedule.builder()
            .groupId(groupId)
            .professorId(1L)
            .courseId(1L)
            .startTime(LocalDateTime.now())
            .endTime(LocalDateTime.of(2023,9,3,14,0))
            .date(LocalDate.now())
            .build();

        entityManager.persistAndFlush(schedule);

        List<Schedule> schedules = scheduleRepository.findScheduleByGroupId(groupId);
        assertEquals(2, schedules.size());
    }

    @Test
    void shouldFindScheduleByProfessorId() {
        Long groupId = 1L;
        Long professorId = 1L;
        Schedule schedule = Schedule.builder()
            .groupId(groupId)
            .courseId(1L)
            .professorId(professorId)
            .startTime(LocalDateTime.now())
            .endTime(LocalDateTime.of(2023,9,3,14,0))
            .date(LocalDate.now())
            .build();
        scheduleRepository.save(schedule);

        List<Schedule> schedules = scheduleRepository.findScheduleByProfessorId(professorId);

        assertEquals(1, schedules.size());
        assertEquals(professorId, schedules.get(0).getProfessorId());
    }

}

