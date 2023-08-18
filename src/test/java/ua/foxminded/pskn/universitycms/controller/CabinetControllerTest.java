package ua.foxminded.pskn.universitycms.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ua.foxminded.pskn.universitycms.model.user.User;
import ua.foxminded.pskn.universitycms.service.user.UserService;

@WebMvcTest(CabinetController.class)
@WithMockUser(authorities = "ROLE_ADMIN")
class CabinetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void shouldGetProfessorCabinetPage() throws Exception {
        User professor = new User();
        professor.setUsername("p");
        professor.setPassword("p");

        when(userService.findProfessorByUsername("test_professor")).thenReturn(professor);

        mockMvc.perform(get("/professorscab")
                .param("username", "test_professor"))
            .andExpect(status().isOk())
            .andExpect(view().name("professorscab"))
            .andExpect(model().attribute("username", professor.getUsername()));
    }

    @Test
    void shouldGetAdminCabinetPage() throws Exception {
        User admin = new User();
        admin.setUsername("test_admin");

        when(userService.findAdminByUsername("test_admin")).thenReturn(admin);

        mockMvc.perform(get("/adminscab")
                .param("username", "test_admin"))
            .andExpect(status().isOk())
            .andExpect(view().name("adminscab"))
            .andExpect(model().attribute("username", admin.getUsername()));
    }

    @Test
    void shouldGetStudentCabinetPage() throws Exception {
        User student = new User();
        student.setUsername("test_student");

        when(userService.findStudentByUsername("test_student")).thenReturn(student);

        mockMvc.perform(get("/studentscab")
                .param("username", "test_student"))
            .andExpect(status().isOk())
            .andExpect(view().name("studentscab"))
            .andExpect(model().attribute("username", student.getUsername()));
    }

}
