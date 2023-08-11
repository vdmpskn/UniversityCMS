package ua.foxminded.pskn.universitycms.controller.university;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ua.foxminded.pskn.universitycms.model.university.Faculty;
import ua.foxminded.pskn.universitycms.service.university.FacultyService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FacultyController.class)
@WithMockUser(authorities = "ROLE_ADMIN")
class FacultyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyService facultyService;

    @Test
    void testFacultyPage() throws Exception {
        List<Faculty> facultyList = new ArrayList<>();
        facultyList.add(new Faculty(1L, 1, "Faculty 1"));
        facultyList.add(new Faculty(2L, 1, "Faculty 2"));

        Pageable pageable = PageRequest.of(0, 5);
        Page<Faculty> facultyPage = new PageImpl<>(facultyList, pageable, facultyList.size());

        when(facultyService.getAllFaculties(any(Pageable.class))).thenReturn(facultyPage);

        mockMvc.perform(get("/faculty"))
            .andExpect(status().isOk())
            .andExpect(view().name("/university/faculty"))
            .andExpect(model().attribute("faculty", facultyPage.getContent()))
            .andExpect(model().attribute("currentPage", 0))
            .andExpect(model().attribute("totalPages", facultyPage.getTotalPages()));
    }

    @Test
    void testAddFaculty() throws Exception {
        mockMvc.perform(post("/faculty/add")
                .param("name", "Faculty Name")
                .param("universityId", "1")
                .with(csrf()) // Include the CSRF token
                .with(user("a").password("a").roles("ADMIN")))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/adminscab"))
            .andExpect(flash().attributeExists("successFacultyMessage"));
    }

    @Test
    void testDeleteFaculty() throws Exception {
        mockMvc.perform(post("/faculty/delete")
                .param("name", "Faculty Name")
                .with(csrf()))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/adminscab"))
            .andExpect(flash().attributeExists("deleteFacultyMessage"));

        verify(facultyService, times(1)).deleteFacultyByName("Faculty Name");
    }
}
