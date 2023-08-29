package ua.foxminded.pskn.universitycms.service.university;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.foxminded.pskn.universitycms.model.university.Schedule;
import ua.foxminded.pskn.universitycms.repository.university.ScheduleRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ScheduleServiceTest {
    @InjectMocks
    private ScheduleService scheduleService;

    @Mock
    private ScheduleRepository scheduleRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetScheduleById() {
        Long groupId = 1L;
        List<Schedule> schedules = Arrays.asList(new Schedule(), new Schedule());
        when(scheduleRepository.findScheduleByGroupId(groupId)).thenReturn(schedules);

        List<Schedule> retrievedSchedules = scheduleService.getScheduleById(groupId);

        assertEquals(schedules, retrievedSchedules);
        verify(scheduleRepository).findScheduleByGroupId(groupId);
    }

    @Test
    void shouldGetScheduleByProfessorId() {
        int professorId = 1;
        List<Schedule> schedules = Arrays.asList(new Schedule(), new Schedule());
        when(scheduleRepository.findScheduleByProfessorId(professorId)).thenReturn(schedules);

        List<Schedule> retrievedSchedules = scheduleService.getScheduleByProfessorId(professorId);

        assertEquals(schedules, retrievedSchedules);
        verify(scheduleRepository).findScheduleByProfessorId(professorId);
    }

    @Test
    void shouldFindAll() {
        List<Schedule> schedules = Arrays.asList(new Schedule(), new Schedule());
        when(scheduleRepository.findAll()).thenReturn(schedules);

        List<Schedule> retrievedSchedules = scheduleService.getAllSchedule();

        assertEquals(schedules, retrievedSchedules);
        verify(scheduleRepository).findAll();
    }
}
