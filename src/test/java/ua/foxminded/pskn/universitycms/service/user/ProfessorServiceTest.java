package ua.foxminded.pskn.universitycms.service.user;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ua.foxminded.pskn.universitycms.converter.professor.ProfessorConverter;
import ua.foxminded.pskn.universitycms.customexception.ProfessorNotFoundException;
import ua.foxminded.pskn.universitycms.dto.ProfessorDTO;
import ua.foxminded.pskn.universitycms.model.user.Professor;
import ua.foxminded.pskn.universitycms.repository.user.ProfessorRepository;

class ProfessorServiceTest {

    @InjectMocks
    private ProfessorService professorService;

    @Mock
    private ProfessorRepository professorRepository;

    @Mock
    private ProfessorConverter professorConverter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetProfessorByUserId_ValidUserId() {
        Long userId = 1L;
        ProfessorDTO expectedProfessorDTO = new ProfessorDTO();
        Professor expectedProfessor = new Professor();

        when(professorRepository.getProfessorByUserId(userId)).thenReturn(Optional.of(expectedProfessor));

        when(professorConverter.convertToDTO(expectedProfessor)).thenReturn(expectedProfessorDTO);

        ProfessorDTO resultProfessorDTO = professorService.getProfessorByUserId(userId);

        assertNotNull(resultProfessorDTO);
        assertEquals(expectedProfessorDTO, resultProfessorDTO);

        verify(professorRepository).getProfessorByUserId(userId);

        verify(professorConverter).convertToDTO(expectedProfessor);
    }

    @Test
    void shouldThrowExceptionIfUserIdIsNull() {
        Long userId = null;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            professorService.getProfessorByUserId(userId);
        });

        assertEquals("Wrong value of 'userId'", exception.getMessage());

        verifyNoInteractions(professorRepository, professorConverter);
    }

    @Test
    void shouldThrowExceptionIfUserIdIsInvalid() {
        Long userId = -1L;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            professorService.getProfessorByUserId(userId);
        });

        assertEquals("Wrong value of 'userId'", exception.getMessage());

        verifyNoInteractions(professorRepository, professorConverter);
    }

    @Test
    void shouldThrowExceptionIfProfessorNotFound() {
        Long userId = 1L;

        when(professorRepository.getProfessorByUserId(userId)).thenReturn(Optional.empty());

        ProfessorNotFoundException exception = assertThrows(ProfessorNotFoundException.class, () -> {
            professorService.getProfessorByUserId(userId);
        });

        assertEquals("Professor not found.", exception.getMessage());

        verify(professorRepository).getProfessorByUserId(userId);

        verifyNoInteractions(professorConverter);
    }
}
