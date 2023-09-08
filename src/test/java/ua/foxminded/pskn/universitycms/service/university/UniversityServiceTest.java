package ua.foxminded.pskn.universitycms.service.university;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import ua.foxminded.pskn.universitycms.converter.university.UniversityDTOToUniversityConverter;
import ua.foxminded.pskn.universitycms.converter.university.UniversityToUniversityDTOConverter;
import ua.foxminded.pskn.universitycms.customexception.UniversityNotFoundException;
import ua.foxminded.pskn.universitycms.dto.UniversityDTO;
import ua.foxminded.pskn.universitycms.model.university.University;
import ua.foxminded.pskn.universitycms.repository.university.UniversityRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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
    void shouldSaveUniversity_ValidUniversity() {
        UniversityDTO universityDTO = new UniversityDTO();
        universityDTO.setUniversityName("Test University");

        University university = new University();
        university.setUniversityId(1L);
        university.setUniversityName("Test University");

        when(toUniversityConverter.convert(universityDTO)).thenReturn(university);
        when(universityRepository.save(university)).thenReturn(university);
        when(toUniversityDTOConverter.convert(university)).thenReturn(universityDTO);

        UniversityDTO savedUniversityDTO = universityService.saveUniversity(universityDTO);

        Assertions.assertNotNull(savedUniversityDTO);
        assertEquals("Test University", savedUniversityDTO.getUniversityName());

        verify(toUniversityConverter).convert(universityDTO);
        verify(universityRepository).save(university);
        verify(toUniversityDTOConverter).convert(university);
    }

    @Test
    void shouldFailToSaveUniversity_BlankName() {
        UniversityDTO universityDTO = new UniversityDTO();
        universityDTO.setUniversityName("");

        assertThrows(IllegalArgumentException.class, () -> universityService.saveUniversity(universityDTO));
    }

    @Test
    void shouldUpdateUniversityName_Success() {
        UniversityDTO universityDTO = new UniversityDTO();
        universityDTO.setUniversityId(1L);
        universityDTO.setUniversityName("New University Name");

        when(universityRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> universityService.updateUniversityName(universityDTO));

        verify(universityRepository).updateUniversityName(1L, "New University Name");
        verify(universityRepository).existsById(1L);
    }

    @Test
    void shouldThrowIllegalArgumentException_WhenUniversityNameIsBlank() {
        UniversityDTO universityDTO = new UniversityDTO();
        universityDTO.setUniversityId(1L);
        universityDTO.setUniversityName("");

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> universityService.updateUniversityName(universityDTO)
        );

        assertEquals("University name cannot be blank.", exception.getMessage());
        verify(universityRepository, never()).existsById(anyLong());
        verify(universityRepository, never()).updateUniversityName(anyLong(), anyString());
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
    void shouldDeleteUniversityByName() {
        UniversityDTO universityDTO = new UniversityDTO();
        universityDTO.setUniversityName("Test University");

        University university = new University();
        university.setUniversityId(1L);

        when(toUniversityConverter.convert(universityDTO)).thenReturn(university);
        when(universityRepository.existsById(1L)).thenReturn(true);
        doNothing().when(universityRepository).delete(university);

        boolean result = universityService.deleteUniversityByName(universityDTO);

        assertTrue(result);

        verify(toUniversityConverter).convert(universityDTO);
        verify(universityRepository).delete(university);
    }

    @Test
    void shouldDeleteUniversity_Success() {
        Long universityId = 1L;

        when(universityRepository.existsById(universityId)).thenReturn(true);

        universityService.deleteUniversity(universityId);

        verify(universityRepository).deleteById(universityId);
    }

    @Test
    void shouldThrowUniversityNotFoundException_WhenUniversityNotFound() {
        Long universityId = 1L;

        when(universityRepository.existsById(universityId)).thenReturn(false);

        assertThrows(UniversityNotFoundException.class, () -> universityService.deleteUniversity(universityId));
    }

    @Test
    void shouldThrowDataIntegrityViolationException_WhenDataIntegrityViolationOccurs() {
        Long universityId = 1L;

        when(universityRepository.existsById(universityId)).thenReturn(true);

        doThrow(DataIntegrityViolationException.class).when(universityRepository).deleteById(universityId);

        assertThrows(DataIntegrityViolationException.class, () -> universityService.deleteUniversity(universityId));
    }

}
