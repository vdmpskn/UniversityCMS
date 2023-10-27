package ua.foxminded.pskn.universitycms.service.university;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import ua.foxminded.pskn.universitycms.converter.schedule.ScheduleConverter;
import ua.foxminded.pskn.universitycms.customexception.ScheduleDeleteException;
import ua.foxminded.pskn.universitycms.customexception.ScheduleUpdateException;
import ua.foxminded.pskn.universitycms.dto.ScheduleDTO;
import ua.foxminded.pskn.universitycms.model.university.Schedule;
import ua.foxminded.pskn.universitycms.repository.university.ScheduleRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertThrows;
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
        Long groupId = 123L;
        List<Schedule> mockScheduleList = new ArrayList<>();
        mockScheduleList.add(new Schedule());

        when(scheduleRepository.findScheduleByGroupId(groupId)).thenReturn(mockScheduleList);

        when(scheduleConverter.convertToDTO(any(Schedule.class))).thenAnswer(
            invocation -> {
                Schedule scheduleEntity = invocation.getArgument(0);
                return new ScheduleDTO();
            });

        List<ScheduleDTO> scheduleDTOList = scheduleService.getScheduleByGroupId(groupId);

        verify(scheduleRepository, times(1)).findScheduleByGroupId(groupId);
        verify(scheduleConverter, times(mockScheduleList.size())).convertToDTO(any(Schedule.class));
    }

    @Test
    void shouldGetScheduleByProfessorId() {
        Long professorId = 456L;
        List<Schedule> mockScheduleList = new ArrayList<>();
        mockScheduleList.add(new Schedule());

        when(scheduleRepository.findScheduleByProfessorId(professorId)).thenReturn(mockScheduleList);

        when(scheduleConverter.convertToDTO(any(Schedule.class))).thenAnswer(
            invocation -> {
                Schedule scheduleEntity = invocation.getArgument(0);
                return new ScheduleDTO();
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

    @Test
    void shouldUpdateSchedule_WithValidData() {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        Schedule scheduleEntity = new Schedule();

        when(scheduleConverter.convertToEntity(scheduleDTO)).thenReturn(scheduleEntity);
        when(scheduleRepository.save(scheduleEntity)).thenReturn(scheduleEntity);

        scheduleService.updateSchedule(scheduleDTO);

        verify(scheduleConverter).convertToEntity(scheduleDTO);
        verify(scheduleRepository).save(scheduleEntity);
    }

    @Test
    void shouldUpdateSchedule_WithNullInput() {
        ScheduleDTO scheduleDTO = null;

        assertThrows(ScheduleUpdateException.class, () -> {
            scheduleService.updateSchedule(scheduleDTO);
        });
    }

    @Test
    void shouldGetAllSchedule_Pageable() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Schedule> scheduleList = new ArrayList<>();

        Page<Schedule> page = new PageImpl<>(scheduleList, pageable, scheduleList.size());

        when(scheduleRepository.findAll(pageable)).thenReturn(page);

        List<ScheduleDTO> scheduleDTOList = scheduleList.stream()
            .map(scheduleConverter::convertToDTO)
            .collect(Collectors.toList());

        Page<ScheduleDTO> resultPage = scheduleService.getAllSchedule(pageable);

        assertEquals(page.getTotalElements(), resultPage.getTotalElements());
        assertEquals(scheduleDTOList.size(), resultPage.getContent().size());
    }

    @Test
    void shouldDeleteScheduleById_WithValidId() {
        Long scheduleId = 1L;

        scheduleService.deleteScheduleById(scheduleId);

        verify(scheduleRepository).deleteById(scheduleId);
    }

    @Test
    void shouldDeleteScheduleById_WithNullId() {
        Long scheduleId = null;

        assertThrows(ScheduleDeleteException.class, () -> {
            scheduleService.deleteScheduleById(scheduleId);
        });
    }
}
