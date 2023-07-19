package ua.foxminded.pskn.universitycms.service.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.foxminded.pskn.universitycms.model.user.Professor;
import ua.foxminded.pskn.universitycms.repository.user.ProfessorRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProfessorServiceTest {

    private ProfessorService professorService;

    @Mock
    private ProfessorRepository professorRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        professorService = new ProfessorService(professorRepository);
    }

    @Test
    void shouldGetProfessorByUserId() {
        Long userId = 1L;
        Professor professor = new Professor();
        when(professorRepository.getProfessorByUserId(userId)).thenReturn(professor);

        Professor retrievedProfessor = professorService.getProfessorByUserId(userId);

        assertEquals(professor, retrievedProfessor);
        verify(professorRepository).getProfessorByUserId(userId);
    }
}
