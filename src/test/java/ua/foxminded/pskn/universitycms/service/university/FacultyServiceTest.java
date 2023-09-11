package ua.foxminded.pskn.universitycms.service.university;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import ua.foxminded.pskn.universitycms.converter.faculty.FacultyDTOToFacultyConverter;
import ua.foxminded.pskn.universitycms.converter.faculty.FacultyToFacultyDTOConverter;
import ua.foxminded.pskn.universitycms.customexception.FacultyEditException;
import ua.foxminded.pskn.universitycms.customexception.FacultyNotFoundException;
import ua.foxminded.pskn.universitycms.dto.FacultyDTO;
import ua.foxminded.pskn.universitycms.model.university.Faculty;
import ua.foxminded.pskn.universitycms.repository.university.FacultyRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class FacultyServiceTest {
    @InjectMocks
    private FacultyService facultyService;

    @Mock
    private FacultyRepository facultyRepository;

    @Mock
    private FacultyDTOToFacultyConverter toFacultyConverter;

    @Mock
    private FacultyToFacultyDTOConverter toFacultyDTOConverter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSaveFaculty_Success() {
        FacultyDTO facultyDTO = new FacultyDTO();
        facultyDTO.setFacultyName("Computer Science");

        Faculty faculty = new Faculty();
        faculty.setFacultyName("Computer Science");

        when(facultyRepository.save(any())).thenReturn(faculty);
        when(toFacultyDTOConverter.convert(any())).thenReturn(facultyDTO);

        FacultyDTO savedFacultyDTO = facultyService.saveFaculty(facultyDTO);

        assertEquals(facultyDTO, savedFacultyDTO);
    }

    @Test
    void shouldFailToSaveFaculty_BlankName() {
        FacultyDTO facultyDTO = new FacultyDTO();
        facultyDTO.setFacultyName("");

        assertThrows(FacultyEditException.class, () -> facultyService.saveFaculty(facultyDTO));
    }

    @Test
    void shouldUpdateFacultyName_Success() {
        FacultyDTO facultyDTO = new FacultyDTO();
        facultyDTO.setFacultyId(1L);
        facultyDTO.setFacultyName("New Faculty Name");

        when(facultyRepository.existsById(anyLong())).thenReturn(true);

        assertDoesNotThrow(() -> facultyService.updateFacultyName(facultyDTO));

        verify(facultyRepository).updateFacultyNameById(eq(1L), eq("New Faculty Name"));
    }

    @Test
    void shouldFailToUpdateFacultyName_FacultyNotFound() {
        FacultyDTO facultyDTO = new FacultyDTO();
        facultyDTO.setFacultyId(1L);
        facultyDTO.setFacultyName("New Faculty Name");

        when(facultyRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(FacultyNotFoundException.class, () -> facultyService.updateFacultyName(facultyDTO));
    }

    @Test
    void shouldFailToUpdateFacultyName_BlankName() {
        FacultyDTO facultyDTO = new FacultyDTO();
        facultyDTO.setFacultyId(1L);
        facultyDTO.setFacultyName("");

        assertThrows(IllegalArgumentException.class, () -> facultyService.updateFacultyName(facultyDTO));
    }

    @Test
    void shouldDeleteFacultyById_Success() {
        Long facultyId = 1L;

        when(facultyRepository.existsById(eq(facultyId))).thenReturn(true);

        assertDoesNotThrow(() -> facultyService.deleteFacultyById(facultyId));

        verify(facultyRepository).deleteById(eq(facultyId));
    }

    @Test
    void shouldFailToDeleteFacultyById_FacultyNotFound() {
        Long facultyId = 1L;

        when(facultyRepository.existsById(eq(facultyId))).thenReturn(false);

        assertThrows(FacultyNotFoundException.class, () -> facultyService.deleteFacultyById(facultyId));
    }

    @Test
    void shouldGetFacultyById() {
        Long facultyId = 1L;
        Faculty faculty = new Faculty();
        faculty.setFacultyId(facultyId);
        when(facultyRepository.findById(facultyId)).thenReturn(Optional.of(faculty));

        Faculty retrievedFaculty = facultyService.getFacultyById(facultyId);

        assertEquals(faculty, retrievedFaculty);
        verify(facultyRepository).findById(facultyId);
    }

    @Test
    void shouldGetAllFaculties() {
        Faculty faculty1 = new Faculty();
        faculty1.setFacultyId(1L);
        Faculty faculty2 = new Faculty();
        faculty2.setFacultyId(2L);
        List<Faculty> faculties = Arrays.asList(faculty1, faculty2);
        when(facultyRepository.findAll()).thenReturn(faculties);

        List<Faculty> retrievedFaculties = facultyService.getAllFaculties();

        assertEquals(faculties, retrievedFaculties);
        verify(facultyRepository).findAll();
    }

}
