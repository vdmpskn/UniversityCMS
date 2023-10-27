package ua.foxminded.pskn.universitycms.controller;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import ua.foxminded.pskn.universitycms.service.user.UserService;

@WebMvcTest(ChangeFacultyController.class)
class ChangeFacultyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser
    void shouldChangeStudentFaculty() throws Exception {
        Long userId = 1L;
        Long newFacultyId = 2L;

        when(userService.changeStudentFaculty(userId, newFacultyId)).thenReturn(true);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
            .post("/studentscab/changefaculty")
            .param("userId", String.valueOf(userId)).with(csrf())
            .param("newFacultyId", String.valueOf(newFacultyId));

        mockMvc.perform(request)
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
            .andExpect(MockMvcResultMatchers.redirectedUrl("/studentscab"))
            .andExpect(MockMvcResultMatchers.flash().attributeExists("successChangeFaculty"));
    }

    @Test
    @WithMockUser
    void shouldChangeProfessorFaculty() throws Exception {
        Long userId = 1L;
        Long newFacultyId = 2L;

        when(userService.changeProfessorFaculty(userId, newFacultyId)).thenReturn(true);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
            .post("/professorscab/changefaculty")
            .param("userId", String.valueOf(userId)).with(csrf())
            .param("newFacultyId", String.valueOf(newFacultyId));

        mockMvc.perform(request)
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
            .andExpect(MockMvcResultMatchers.redirectedUrl("/professorscab"))
            .andExpect(MockMvcResultMatchers.flash().attributeExists("successChangeFaculty"));
    }
}

