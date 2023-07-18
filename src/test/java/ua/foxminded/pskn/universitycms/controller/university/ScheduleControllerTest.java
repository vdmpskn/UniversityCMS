package ua.foxminded.pskn.universitycms.controller.university;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import ua.foxminded.pskn.universitycms.model.university.Schedule;
import ua.foxminded.pskn.universitycms.repository.university.ScheduleRepository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ScheduleControllerTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private Model model;

    private ScheduleController scheduleController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        scheduleController = new ScheduleController(scheduleRepository);
    }

    @Test
    void schedulePage_ShouldReturnScheduleTemplate() {
        List<Schedule> scheduleList = Arrays.asList(
            new Schedule(1L, 1, 1, 1, Timestamp.valueOf("2022-07-01 12:00:00"), Timestamp.valueOf("2022-07-01 13:00:00"), new Date(2022 - 1900, 6, 1)),
            new Schedule(2L, 2, 2, 2, Timestamp.valueOf("2022-07-01 14:00:00"), Timestamp.valueOf("2022-07-01 15:00:00"), new Date(2022 - 1900, 6, 2))
        );
        when(scheduleRepository.findAll()).thenReturn(scheduleList);

        String viewName = scheduleController.schedulePage(model);

        assertEquals("schedule", viewName);
        verify(model).addAttribute("schedule", scheduleList);
        verify(scheduleRepository).findAll();
    }
}

