package ua.foxminded.pskn.universitycms.controller.user;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

import ua.foxminded.pskn.universitycms.dto.ProfessorDTO;
import ua.foxminded.pskn.universitycms.service.user.ProfessorService;


class ProfessorControllerTest {

    @InjectMocks
    private ProfessorController professorController;

    @Mock
    private ProfessorService professorService;

    @Mock
    private Model model;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void shouldGetProfessorPage() {
        int page = 0;
        int pageSize = 5;
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<ProfessorDTO> mockProfessorPage = mock(Page.class);

        List<ProfessorDTO> mockProfessors = new ArrayList<>();
        mockProfessors.add(new ProfessorDTO());
        mockProfessors.add(new ProfessorDTO());

        when(professorService.getAllProfessors(pageable)).thenReturn(mockProfessorPage);
        when(mockProfessorPage.getContent()).thenReturn(mockProfessors);
        when(mockProfessorPage.getTotalPages()).thenReturn(3);

        String viewName = professorController.professorPage(model, page, pageSize);

        assertEquals("/users/professors", viewName);
        verify(model).addAttribute("professors", mockProfessorPage.getContent());
        verify(model).addAttribute("currentPage", page);
        verify(model).addAttribute("totalPages", mockProfessorPage.getTotalPages());
    }
}
