package ua.foxminded.pskn.universitycms.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import ua.foxminded.pskn.universitycms.dto.FacultyDTO;
import ua.foxminded.pskn.universitycms.dto.UserDTO;
import ua.foxminded.pskn.universitycms.model.usercabinetdata.StudentCabinetData;
import ua.foxminded.pskn.universitycms.service.university.FacultyService;
import ua.foxminded.pskn.universitycms.service.user.UserService;
import ua.foxminded.pskn.universitycms.service.usercabinet.UserCabinetService;

@WebMvcTest(CabinetController.class)
@WithMockUser(authorities = "ROLE_ADMIN")
class CabinetControllerTest {
    @Autowired
    private CabinetController cabinetController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserCabinetService userCabinetService;

    @MockBean
    private FacultyService facultyService;

    @MockBean
    private Model model;

    @Test
    void shouldGetProfessorCabinetPage() throws Exception {
        UserDTO professor = new UserDTO();
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
        CabinetController adminController = new CabinetController(userService, userCabinetService, facultyService);
        Model model = mock(Model.class);

        String viewName = adminController.adminCabinetPage();

        assertEquals("adminscab", viewName);
    }

    @Test
    void shouldGetStudentCabinetPage() {
        String studentName = "student123";

        StudentCabinetData mockCabinetData = new StudentCabinetData();

        List<FacultyDTO> mockFaculties = List.of(new FacultyDTO());

        when(userCabinetService.getStudentCabinetData(studentName)).thenReturn(mockCabinetData);

        when(facultyService.findAll()).thenReturn(mockFaculties);

        String viewName = cabinetController.studentCabinetPage(studentName, model);

        assertEquals("studentscab", viewName);
        verify(model).addAttribute("username", mockCabinetData.getUsername());
        verify(model).addAttribute("studentId", mockCabinetData.getStudentId());
        verify(model).addAttribute("studentGroup", mockCabinetData.getStudentGroup());
        verify(model).addAttribute("availableGroups", mockCabinetData.getAvailableGroups());
        verify(model).addAttribute("availableFaculties", mockFaculties);
    }

}
