package ua.foxminded.pskn.universitycms.service.university;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.foxminded.pskn.universitycms.dto.UniversityDTO;
import ua.foxminded.pskn.universitycms.model.university.University;
import ua.foxminded.pskn.universitycms.repository.university.UniversityRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class UniversityServiceTest {

    private UniversityService universityService;

    @Mock
    private UniversityRepository universityRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        universityService = new UniversityService(universityRepository);
    }

    @Test
    void shouldSaveUniversity() {
        University university = new University();
        university.setUniversityId(1L);
        when(universityRepository.save(university)).thenReturn(university);

        University savedUniversity = universityService.saveUniversity(UniversityDTO.fromUniversity(university));

        assertEquals(university, savedUniversity);
        verify(universityRepository).save(university);
    }

    @Test
    void shouldGetUniversityById() {
        Long universityId = 1L;
        University university = new University();
        university.setUniversityId(universityId);
        when(universityRepository.findById(universityId)).thenReturn(Optional.of(university));

        Optional<University> retrievedUniversity = Optional.ofNullable(universityService.getUniversityById(universityId));

        assertTrue(retrievedUniversity.isPresent());
        assertEquals(university, retrievedUniversity.get());
        verify(universityRepository).findById(universityId);
    }


    @Test
    void shouldGetAllUniversities() {
        University university1 = new University();
        university1.setUniversityId(1L);
        University university2 = new University();
        university2.setUniversityId(2L);
        List<University> universities = Arrays.asList(university1, university2);
        when(universityRepository.findAll()).thenReturn(universities);

        List<University> retrievedUniversities = universityService.getAllUniversities();

        assertEquals(universities, retrievedUniversities);
        verify(universityRepository).findAll();
    }

    @Test
    void shouldDeleteUniversity() {
        Long universityId = 1L;

        universityService.deleteUniversity(universityId);

        verify(universityRepository).deleteById(universityId);
    }
}
