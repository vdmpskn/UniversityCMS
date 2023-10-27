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

@WebMvcTest(ChangeUsernameController.class)
@WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
class ChangeUsernameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void shouldChangeStudentUsername() throws Exception {
        Long userId = 1L;
        String newUsername = "newUsername";

        when(userService.changeUsername(userId, newUsername)).thenReturn(true);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
            .post("/studentscab/changeusername")
            .param("userId", String.valueOf(userId)).with(csrf())
            .param("newUsername", newUsername);

        mockMvc.perform(request)
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
            .andExpect(MockMvcResultMatchers.redirectedUrl("/login"))
            .andExpect(MockMvcResultMatchers.flash().attributeExists("successfulChangeStudentName"));
    }

    @Test
    void shouldChangeProfessorUsername() throws Exception {
        Long userId = 1L;
        String newUsername = "newUsername";

        when(userService.changeUsername(userId, newUsername)).thenReturn(true);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
            .post("/professorscab/changeusername")
            .param("userId", String.valueOf(userId)).with(csrf())
            .param("newUsername", newUsername);

        mockMvc.perform(request)
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
            .andExpect(MockMvcResultMatchers.redirectedUrl("/login"))
            .andExpect(MockMvcResultMatchers.flash().attributeExists("successfulChangeProfessorName"));
    }
}

