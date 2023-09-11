package ua.foxminded.pskn.universitycms.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.foxminded.pskn.universitycms.model.university.StudentGroup;
import ua.foxminded.pskn.universitycms.model.user.User;
import ua.foxminded.pskn.universitycms.model.usercabinetdata.StudentCabinetData;
import ua.foxminded.pskn.universitycms.service.user.UserService;
import ua.foxminded.pskn.universitycms.service.usercabinet.UserCabinetService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CabinetController.class)
@WithMockUser(authorities = "ROLE_ADMIN")
class CabinetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserCabinetService userCabinetService;

    @Test
    void shouldGetProfessorCabinetPage() throws Exception {
        User professor = new User();
        professor.setUsername("p");
        professor.setPassword("p");

        when(userService.findProfessorByUsername("test_professor")).thenReturn(Optional.of(professor));

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

        when(userService.findAdminByUsername("test_admin")).thenReturn(Optional.of(admin));

        mockMvc.perform(get("/adminscab")
                .param("username", "test_admin"))
            .andExpect(status().isOk())
            .andExpect(view().name("adminscab"))
            .andExpect(model().attribute("username", admin.getUsername()));
    }

    @Test
    void shouldGetStudentCabinetPage() throws Exception {
        CabinetController controller = new CabinetController(null, userCabinetService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        StudentCabinetData cabinetData = new StudentCabinetData();
        cabinetData.setUsername("test_student");
        cabinetData.setStudentId(1L);
        cabinetData.setStudentGroup("Group 1");
        List<StudentGroup> availableGroups = new ArrayList<>();

        StudentGroup group1 = new StudentGroup();
        group1.setGroupName("Group 1");
        availableGroups.add(group1);

        StudentGroup group2 = new StudentGroup();
        group2.setGroupName("Group 2");
        availableGroups.add(group2);

        cabinetData.setAvailableGroups(availableGroups);

        when(userCabinetService.getStudentCabinetData("test_student")).thenReturn(cabinetData);

        mockMvc.perform(MockMvcRequestBuilders.get("/studentscab")
                .param("username", "test_student"))
            .andExpect(status().isOk())
            .andExpect(view().name("studentscabview"))
            .andExpect(model().attribute("username", "test_student"))
            .andExpect(model().attribute("studentId", 1L))
            .andExpect(model().attribute("studentGroup", "Group 1"))
            .andExpect(model().attribute("availableGroups", availableGroups));

        verify(userCabinetService, times(1)).getStudentCabinetData("test_student");
    }

}
