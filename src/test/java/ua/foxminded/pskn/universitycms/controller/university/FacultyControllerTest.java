package ua.foxminded.pskn.universitycms.controller.university;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;
import ua.foxminded.pskn.universitycms.model.university.Faculty;
import ua.foxminded.pskn.universitycms.service.university.FacultyService;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FacultyController.class)
class FacultyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyService facultyService;

    @Test
    void shouldGetFacultyPage() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setFacultyId(1L);
        faculty.setFacultyName("Computer Science");

        Page<Faculty> facultyPage = new PageImpl<>(Collections.singletonList(faculty));
        when(facultyService.getAllFaculties(any())).thenReturn(facultyPage);

        mockMvc.perform(get("/faculty"))
            .andExpect(status().isOk())
            .andExpect(view().name("/university/faculty"))
            .andExpect(model().attributeExists("faculty"))
            .andExpect(model().attribute("faculty", facultyPage.getContent()))
            .andExpect(model().attributeExists("currentPage"))
            .andExpect(model().attribute("currentPage", 0))
            .andExpect(model().attributeExists("totalPages"))
            .andExpect(model().attribute("totalPages", facultyPage.getTotalPages()));
    }
}
