package ua.foxminded.pskn.universitycms.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import ua.foxminded.pskn.universitycms.dto.ScheduleDTO;
import ua.foxminded.pskn.universitycms.service.university.ScheduleService;

@WebMvcTest(GetScheduleController.class)
@WithMockUser(roles = "ADMIN")
class GetScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ScheduleService scheduleService;

    @Test
    void shouldGetProfessorsSchedule() throws Exception {
        Long userId = 1L;
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        scheduleDTOList.add(new ScheduleDTO());
        scheduleDTOList.add(new ScheduleDTO());

        when(scheduleService.getProfessorSchedule(userId)).thenReturn(scheduleDTOList);

        ResultActions resultActions = mockMvc.perform(get("/professorscab/schedule").param("userId", String.valueOf(userId)));

        resultActions.andExpect(status().isOk())
            .andExpect(model().attributeExists("professorsSchedule"))
            .andExpect(view().name("professorschedule"));
    }

    @Test
    void shouldGetStudentSchedule() throws Exception {
        Long userId = 1L;
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        scheduleDTOList.add(new ScheduleDTO());
        scheduleDTOList.add(new ScheduleDTO());

        when(scheduleService.getStudentSchedule(userId)).thenReturn(scheduleDTOList);

        ResultActions resultActions = mockMvc.perform(get("/studentscab/schedule").param("userId", String.valueOf(userId)));

        resultActions.andExpect(status().isOk())
            .andExpect(model().attributeExists("studentSchedule"))
            .andExpect(view().name("studentschedule"));
    }
}

