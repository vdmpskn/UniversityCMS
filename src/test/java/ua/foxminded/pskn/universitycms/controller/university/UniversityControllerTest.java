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
import ua.foxminded.pskn.universitycms.dto.UniversityDTO;
import ua.foxminded.pskn.universitycms.model.university.University;
import ua.foxminded.pskn.universitycms.service.university.FacultyService;
import ua.foxminded.pskn.universitycms.service.university.UniversityService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UniversityController.class)
@WithMockUser(authorities = "ROLE_ADMIN")
class UniversityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UniversityService universityService;

    @MockBean
    private FacultyService facultyService;

    @Test
    void shouldUniversityPage() throws Exception {
        List<UniversityDTO> universityList = new ArrayList<>();
        universityList.add(new UniversityDTO(1L, "University 1"));
        universityList.add(new UniversityDTO(2L, "University 2"));

        Pageable pageable = PageRequest.of(0, 5);
        Page<UniversityDTO> universityPage = new PageImpl<>(universityList, pageable, universityList.size());

        when(universityService.getAllUniversities(any(Pageable.class))).thenReturn(universityPage);

        mockMvc.perform(get("/university"))
            .andExpect(status().isOk())
            .andExpect(view().name("university/university"))
            .andExpect(model().attribute("university", universityPage.getContent()))
            .andExpect(model().attribute("currentPage", 0))
            .andExpect(model().attribute("totalPages", universityPage.getTotalPages()));
    }

    @Test
    void shouldAddUniversity() throws Exception {
        mockMvc.perform(post("/university/add")
                .param("name", "Test University")
                .with(csrf()))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/university"))
            .andExpect(flash().attributeExists("successUniversityMessage"));

        verify(universityService, times(1)).saveUniversity(any(UniversityDTO.class));
    }

    @Test
    void shouldDeleteUniversity() throws Exception {
        mockMvc.perform(post("/university/delete")
                .param("universityId", "1")
                .with(csrf()))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/university"))
            .andExpect(flash().attributeExists("deleteUniversityMessage"));

        verify(universityService, times(1)).deleteUniversityByName(any(UniversityDTO.class));
    }

    @Test
    void shouldEditUniversity() throws Exception {
        mockMvc.perform(post("/university/edit")
                .param("universityId", "1")
                .param("name", "Updated University")
                .with(csrf()))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/university"))
            .andExpect(flash().attributeExists("editUniversityMessage"));

        verify(universityService, times(1)).updateUniversityName(any(UniversityDTO.class));
    }

}
