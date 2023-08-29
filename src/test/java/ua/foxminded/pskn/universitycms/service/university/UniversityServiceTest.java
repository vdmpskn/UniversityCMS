package ua.foxminded.pskn.universitycms.service.university;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.foxminded.pskn.universitycms.converter.university.UniversityDTOToUniversityConverter;
import ua.foxminded.pskn.universitycms.converter.university.UniversityToUniversityDTOConverter;
import ua.foxminded.pskn.universitycms.dto.UniversityDTO;
import ua.foxminded.pskn.universitycms.model.university.University;
import ua.foxminded.pskn.universitycms.repository.university.UniversityRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class UniversityServiceTest {

    @InjectMocks
    private UniversityService universityService;

    @Mock
    private UniversityDTOToUniversityConverter toUniversityConverter;

    @Mock
    private UniversityToUniversityDTOConverter toUniversityDTOConverter;

    @Mock
    private UniversityRepository universityRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSaveUniversity_Success() {
        UniversityDTO universityDTO = new UniversityDTO();
        universityDTO.setUniversityName("Test University");

        University convertedUniversity = new University();
        when(toUniversityConverter.convert(universityDTO)).thenReturn(convertedUniversity);

        when(universityRepository.save(convertedUniversity)).thenReturn(convertedUniversity);

        University savedUniversity = universityService.saveUniversity(universityDTO);

        assertNotNull(savedUniversity);
        assertEquals(convertedUniversity, savedUniversity);
        verify(toUniversityConverter, times(1)).convert(universityDTO);
        verify(universityRepository, times(1)).save(convertedUniversity);
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
