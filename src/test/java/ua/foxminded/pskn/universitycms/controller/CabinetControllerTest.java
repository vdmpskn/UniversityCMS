package ua.foxminded.pskn.universitycms.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import ua.foxminded.pskn.universitycms.dto.FacultyDTO;
import ua.foxminded.pskn.universitycms.dto.UserDTO;
import ua.foxminded.pskn.universitycms.model.university.StudentGroup;
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
    private Authentication authentication;

    @MockBean
    private Model model;

    @Test
    @WithMockUser(username = "test_professor", authorities = "ROLE_PROFESSOR")
    void shouldGetProfessorCabinetPage() throws Exception {
        UserDTO professor = new UserDTO();
        professor.setUsername("p");
        professor.setPassword("p");

        when(userService.findProfessorByUsername("test_professor")).thenReturn(Optional.of(professor));

        mockMvc.perform(get("/professorscab"))
            .andExpect(status().isOk())
            .andExpect(view().name("professorscab"));
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
        StudentCabinetData mockCabinetData = new StudentCabinetData();
        mockCabinetData.setUsername("student123");
        mockCabinetData.setUserID(1L);
        mockCabinetData.setStudentId(1L);
        mockCabinetData.setStudentGroup("IS-21");
        mockCabinetData.setAvailableGroups(Collections.singletonList(new StudentGroup()));
        when(authentication.getName()).thenReturn("student123");
        when(userCabinetService.getStudentCabinetData("student123")).thenReturn(mockCabinetData);
        when(facultyService.findAll()).thenReturn(Collections.singletonList(new FacultyDTO()));

        String viewName = cabinetController.studentCabinetPage(authentication, model);

        assertEquals("studentscab", viewName);
        verify(model).addAttribute("username", "student123");
        verify(model).addAttribute("userId", 1L);
        verify(model).addAttribute("studentId", 1L);
        verify(model).addAttribute("studentGroup", "IS-21");
        verify(model).addAttribute("availableGroups", mockCabinetData.getAvailableGroups());
        verify(model).addAttribute("availableFaculties", Collections.singletonList(new FacultyDTO()));
    }

}
