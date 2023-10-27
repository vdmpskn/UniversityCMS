package ua.foxminded.pskn.universitycms.controller.university;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import ua.foxminded.pskn.universitycms.customexception.ScheduleCreateException;
import ua.foxminded.pskn.universitycms.customexception.ScheduleUpdateException;
import ua.foxminded.pskn.universitycms.dto.ScheduleDTO;
import ua.foxminded.pskn.universitycms.service.university.ScheduleService;

@WebMvcTest(ScheduleController.class)
@WithMockUser(authorities = "ROLE_ADMIN")
class ScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ScheduleService scheduleService;

    @Test
    void shouldGetSchedulePage() throws Exception {
        ScheduleDTO schedule = ScheduleDTO.builder()
            .scheduleId(1L)
            .courseId(1L)
            .groupId(1L)
            .professorId(2L)
            .startTime(LocalDateTime.now())
            .endTime(LocalDateTime.of(2023, 9, 3, 14, 0))
            .date(LocalDate.now())
            .build();


        Page<ScheduleDTO> schedulePage = new PageImpl<>(Collections.singletonList(schedule));
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

    @Test
    void shouldAddSchedule_Success() throws Exception {
        ScheduleDTO scheduleDTO = new ScheduleDTO();

        doNothing().when(scheduleService).saveSchedule(scheduleDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
            .post("/schedule/add")
            .with(csrf())
            .flashAttr("scheduleDTO", scheduleDTO);

        mockMvc.perform(request)
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
            .andExpect(MockMvcResultMatchers.redirectedUrl("/schedule"))
            .andExpect(MockMvcResultMatchers.flash().attributeExists("successAddSchedule"));
    }

    @Test
    void shouldAddSchedule_Failure() throws Exception {
        ScheduleDTO scheduleDTO = new ScheduleDTO();

        doThrow(new ScheduleCreateException("Fail added schedule")).when(scheduleService).saveSchedule(scheduleDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
            .post("/schedule/add")
            .with(csrf())
            .flashAttr("scheduleDTO", scheduleDTO);

        mockMvc.perform(request)
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
            .andExpect(MockMvcResultMatchers.redirectedUrl("/schedule"))
            .andExpect(MockMvcResultMatchers.flash().attributeExists("failAddSchedule"));
    }

    @Test
    void shouldEditSchedule_Success() throws Exception {
        ScheduleDTO scheduleDTO = new ScheduleDTO();

        doNothing().when(scheduleService).updateSchedule(scheduleDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
            .post("/schedule/edit")
            .with(csrf())
            .flashAttr("scheduleDTO", scheduleDTO);

        mockMvc.perform(request)
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
            .andExpect(MockMvcResultMatchers.redirectedUrl("/schedule"))
            .andExpect(MockMvcResultMatchers.flash().attributeExists("successEditSchedule"));
    }

    @Test
    void shouldEditSchedule_Failure() throws Exception {
        ScheduleDTO scheduleDTO = new ScheduleDTO();

        doThrow(new ScheduleUpdateException("Fail edited schedule")).when(scheduleService).updateSchedule(scheduleDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
            .post("/schedule/edit")
            .with(csrf())
            .flashAttr("scheduleDTO", scheduleDTO);

        mockMvc.perform(request)
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
            .andExpect(MockMvcResultMatchers.redirectedUrl("/schedule"))
            .andExpect(MockMvcResultMatchers.flash().attributeExists("failEditSchedule"));
    }

    @Test
    void shouldDeleteSchedule_Success() throws Exception {
        Long scheduleId = 1L;

        doNothing().when(scheduleService).deleteScheduleById(scheduleId);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
            .post("/schedule/delete")
            .with(csrf())
            .flashAttr("scheduleDTO", new ScheduleDTO());

        mockMvc.perform(request)
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
            .andExpect(MockMvcResultMatchers.redirectedUrl("/schedule"))
            .andExpect(MockMvcResultMatchers.flash().attributeExists("successDeleteSchedule"));
    }

}
