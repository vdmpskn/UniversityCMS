package ua.foxminded.pskn.universitycms.controller.university;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ua.foxminded.pskn.universitycms.dto.FacultyDTO;
import ua.foxminded.pskn.universitycms.model.university.Faculty;
import ua.foxminded.pskn.universitycms.service.university.FacultyService;
import ua.foxminded.pskn.universitycms.service.university.UniversityService;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FacultyController.class)
@WithMockUser(authorities = "ROLE_ADMIN")
class FacultyControllerTest {

    @InjectMocks
    private FacultyController facultyController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyService facultyService;

    @MockBean
    private UniversityService universityService;

    @Test
    void shouldShowFacultyPage() throws Exception {
        Page<Faculty> facultyPage = new PageImpl<>(Collections.singletonList(new Faculty()));
        when(facultyService.getAllFaculties(any(Pageable.class))).thenReturn(facultyPage);

        mockMvc.perform(get("/faculty"))
            .andExpect(status().isOk())
            .andExpect(view().name("/university/faculty"))
            .andExpect(model().attributeExists("faculty", "currentPage", "totalPages", "facultyDTO"));
    }

    @Test
    void shouldAddFaculty_FacultyNotFound() throws Exception {
        when(universityService.isUniversityExistByUniversityId(anyInt())).thenReturn(false);

        mockMvc.perform(post("/faculty/add")
                .param("universityId", "1")
                .param("facultyName", "Test Faculty")
                .with(csrf()))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/faculty"))
            .andExpect(flash().attributeExists("errorFacultyMessage"))
            .andExpect(flash().attributeCount(1));

        verify(facultyService, never()).saveFaculty(any(FacultyDTO.class));
    }

    @Test
    void shouldAddFaculty_Success() throws Exception {
        when(universityService.isUniversityExistByUniversityId(anyInt())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/faculty/add")
                .param("universityId", "1")
                .param("facultyName", "Test Faculty")
                .with(csrf()))
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
            .andExpect(MockMvcResultMatchers.redirectedUrl("/faculty"))
            .andExpect(MockMvcResultMatchers.flash().attributeExists("successFacultyMessage"))
            .andExpect(MockMvcResultMatchers.flash().attributeCount(1));

        verify(facultyService, times(1)).saveFaculty(any(FacultyDTO.class));
    }

    @Test
    void shouldDeleteFaculty_Success() throws Exception {
        doNothing().when(facultyService).deleteFacultyById(1L);

        mockMvc.perform(post("/faculty/delete")
                .param("facultyId", "1")
                .with(csrf()))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/faculty"))
            .andExpect(flash().attributeExists("deleteFacultyMessage"));

        verify(facultyService, times(1)).deleteFacultyById(anyLong());
    }

    @Test
    void shouldEditFaculty_Success() throws Exception {
        FacultyDTO facultyDTO = new FacultyDTO();
        facultyDTO.setFacultyId(1L);
        facultyDTO.setFacultyName("Updated Faculty");

        mockMvc.perform(post("/faculty/edit")
                .flashAttr("facultyDTO", facultyDTO)
                .with(csrf()))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/faculty"))
            .andExpect(flash().attributeExists("editFacultyMessage"));

        verify(facultyService, times(1)).updateFacultyName(any(FacultyDTO.class));
    }

    @Test
    void shouldFailEditFaculty_WhenDataIsMissing() throws Exception {
        mockMvc.perform(post("/faculty/edit")
                .param("facultyId", "1")
                .with(csrf()))
            .andExpect(status().is3xxRedirection()) // Ожидаем статус перенаправления (3xx)
            .andExpect(redirectedUrl("/faculty"))
            .andExpect(flash().attributeExists("failToEditFacultyMessage"));

        verify(facultyService, never()).updateFacultyName(any(FacultyDTO.class));
    }

}
