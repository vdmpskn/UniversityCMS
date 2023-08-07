package ua.foxminded.pskn.universitycms.service.university;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.foxminded.pskn.universitycms.model.university.Faculty;
import ua.foxminded.pskn.universitycms.repository.university.FacultyRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class FacultyServiceTest {

    private FacultyService facultyService;

    @Mock
    private FacultyRepository facultyRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        facultyService = new FacultyService(facultyRepository);
    }

    @Test
    void shouldSaveFaculty() {
        Faculty faculty = new Faculty();
        faculty.setFacultyId(1L);
        when(facultyRepository.save(faculty)).thenReturn(faculty);

        Faculty savedFaculty = facultyService.saveFaculty(faculty);

        assertEquals(faculty, savedFaculty);
        verify(facultyRepository).save(faculty);
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

    @Test
    void shouldDeleteFaculty() {
        Long facultyId = 1L;

        facultyService.deleteFaculty(facultyId);

        verify(facultyRepository).deleteById(facultyId);
    }
}
