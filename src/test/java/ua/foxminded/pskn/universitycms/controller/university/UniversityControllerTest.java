package ua.foxminded.pskn.universitycms.controller.university;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;
import ua.foxminded.pskn.universitycms.model.university.University;
import ua.foxminded.pskn.universitycms.service.university.UniversityService;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UniversityController.class)
class UniversityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UniversityService universityService;

    @Test
    void testUniversityPage() throws Exception {
        University university = new University();
        university.setUniversityId(1L);
        university.setUniversityName("KNU");

        Page<University> universityPage = new PageImpl<>(Collections.singletonList(university));
        when(universityService.getAllUniversities(any())).thenReturn(universityPage);

        mockMvc.perform(get("/university"))
            .andExpect(status().isOk())
            .andExpect(view().name("/university/university"))
            .andExpect(model().attributeExists("university"))
            .andExpect(model().attribute("university", universityPage.getContent()))
            .andExpect(model().attributeExists("currentPage"))
            .andExpect(model().attribute("currentPage", 0))
            .andExpect(model().attributeExists("totalPages"))
            .andExpect(model().attribute("totalPages", universityPage.getTotalPages()));
    }
}
