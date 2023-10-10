package ua.foxminded.pskn.universitycms.service.university;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ua.foxminded.pskn.universitycms.converter.schedule.ScheduleConverter;
import ua.foxminded.pskn.universitycms.dto.ScheduleDTO;
import ua.foxminded.pskn.universitycms.model.university.Schedule;
import ua.foxminded.pskn.universitycms.repository.university.ScheduleRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ScheduleServiceTest {
    @InjectMocks
    private ScheduleService scheduleService;

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private ScheduleConverter scheduleConverter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void shouldGetScheduleById() {
        int groupId = 123;
        List<Schedule> mockScheduleList = new ArrayList<>();
        mockScheduleList.add(new Schedule());

        when(scheduleRepository.findScheduleByGroupId(groupId)).thenReturn(mockScheduleList);

        when(scheduleConverter.convertToDTO(any(Schedule.class))).thenAnswer(
            invocation -> {
                Schedule scheduleEntity = invocation.getArgument(0);
                return new ScheduleDTO();
            });

        List<ScheduleDTO> scheduleDTOList = scheduleService.getScheduleById(groupId);

        verify(scheduleRepository, times(1)).findScheduleByGroupId(groupId);
        verify(scheduleConverter, times(mockScheduleList.size())).convertToDTO(any(Schedule.class));
    }

    @Test
    void shouldGetScheduleByProfessorId() {
        int professorId = 456;
        List<Schedule> mockScheduleList = new ArrayList<>();
        mockScheduleList.add(new Schedule());

        when(scheduleRepository.findScheduleByProfessorId(professorId)).thenReturn(mockScheduleList);

        when(scheduleConverter.convertToDTO(any(Schedule.class))).thenAnswer(
            invocation -> {
                Schedule scheduleEntity = invocation.getArgument(0);
                return new ScheduleDTO(/* map entity to DTO */);
            });

        List<ScheduleDTO> scheduleDTOList = scheduleService.getScheduleByProfessorId(professorId);

        verify(scheduleRepository, times(1)).findScheduleByProfessorId(professorId);
        verify(scheduleConverter, times(mockScheduleList.size())).convertToDTO(any(Schedule.class));
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
