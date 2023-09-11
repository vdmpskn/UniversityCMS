package ua.foxminded.pskn.universitycms.service.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.foxminded.pskn.universitycms.model.user.Professor;
import ua.foxminded.pskn.universitycms.repository.user.ProfessorRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProfessorServiceTest {

    @InjectMocks
    private ProfessorService professorService;

    @Mock
    private ProfessorRepository professorRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetProfessorByUserId() {
        Long userId = 1L;
        Professor professor = new Professor();
        when(professorRepository.getProfessorByUserId(userId)).thenReturn(Optional.of(professor));

        Optional<Professor> retrievedProfessor = professorService.getProfessorByUserId(userId);

        assertEquals(professor, retrievedProfessor.orElse(null));
        verify(professorRepository).getProfessorByUserId(userId);
    }
}
