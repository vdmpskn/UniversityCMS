package ua.foxminded.pskn.universitycms.controller.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ua.foxminded.pskn.universitycms.model.user.Professor;
import ua.foxminded.pskn.universitycms.service.user.ProfessorService;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProfessorController.class)
@WithMockUser(authorities = "ROLE_ADMIN")
class ProfessorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfessorService professorService;

    @Test
    void shouldGetProfessorPage() throws Exception {
        Professor professor = new Professor();
        professor.setProfessorId(1);
        professor.setUserId(1L);

        Page<Professor> professorPage = new PageImpl<>(Collections.singletonList(professor));
        when(professorService.getAllProfessors(any())).thenReturn(professorPage);

        mockMvc.perform(get("/professors"))
            .andExpect(status().isOk())
            .andExpect(view().name("/users/professors"))
            .andExpect(model().attributeExists("professors"))
            .andExpect(model().attribute("professors", professorPage.getContent()))
            .andExpect(model().attributeExists("currentPage"))
            .andExpect(model().attribute("currentPage", 0))
            .andExpect(model().attributeExists("totalPages"))
            .andExpect(model().attribute("totalPages", professorPage.getTotalPages()));
    }
}
