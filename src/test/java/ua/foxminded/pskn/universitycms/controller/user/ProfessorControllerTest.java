package ua.foxminded.pskn.universitycms.controller.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import ua.foxminded.pskn.universitycms.model.user.Professor;
import ua.foxminded.pskn.universitycms.repository.user.ProfessorRepository;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ProfessorControllerTest {

    @Mock
    private ProfessorRepository professorRepository;

    @Mock
    private Model model;

    private ProfessorController professorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        professorController = new ProfessorController(professorRepository);
    }

    @Test
    void professorPage_ShouldReturnProfessorsTemplate() {
        List<Professor> professorList = Arrays.asList(
            new Professor(1L, 1),
            new Professor(2L, 2)
        );
        when(professorRepository.findAll()).thenReturn(professorList);

        String viewName = professorController.professorPage(model);

        assertEquals("professors", viewName);
        verify(model).addAttribute("professors", professorList);
        verify(professorRepository).findAll();
    }
}
