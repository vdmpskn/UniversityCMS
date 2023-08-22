package ua.foxminded.pskn.universitycms.controller.university;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ua.foxminded.pskn.universitycms.model.university.Schedule;
import ua.foxminded.pskn.universitycms.service.university.ScheduleService;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ScheduleController.class)
@WithMockUser(authorities = "ROLE_ADMIN")
class ScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ScheduleService scheduleService;

    @Test
    void shouldGetSchedulePage() throws Exception {
        Schedule schedule = Schedule.builder()
            .scheduleId(1L)
            .courseId(1)
            .groupId(1)
            .professorId(2)
            .startTime(Timestamp.valueOf("2023-07-19 15:30:00"))
            .endTime(Timestamp.valueOf("2023-07-19 17:30:00"))
            .date(Date.valueOf("2023-07-19"))
            .build();


        Page<Schedule> schedulePage = new PageImpl<>(Collections.singletonList(schedule));
        when(scheduleService.getAllSchedule(any())).thenReturn(schedulePage);

        mockMvc.perform(get("/schedule"))
            .andExpect(status().isOk())
            .andExpect(view().name("/university/schedule"))
            .andExpect(model().attributeExists("schedule"))
            .andExpect(model().attribute("schedule", schedulePage.getContent()))
            .andExpect(model().attributeExists("currentPage"))
            .andExpect(model().attribute("currentPage", 0))
            .andExpect(model().attributeExists("totalPages"))
            .andExpect(model().attribute("totalPages", schedulePage.getTotalPages()));
    }
}
