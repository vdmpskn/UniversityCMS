package ua.foxminded.pskn.universitycms.service.university;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import ua.foxminded.pskn.universitycms.converter.university.UniversityConverter;
import ua.foxminded.pskn.universitycms.customexception.UniversityNotFoundException;
import ua.foxminded.pskn.universitycms.dto.UniversityDTO;
import ua.foxminded.pskn.universitycms.model.university.University;
import ua.foxminded.pskn.universitycms.repository.university.UniversityRepository;


class UniversityServiceTest {

    @InjectMocks
    private UniversityService universityService;

    @Mock
    private UniversityConverter universityConverter;

    @Mock
    private UniversityRepository universityRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void shouldSaveUniversity_Success() {
        UniversityDTO universityDTO = UniversityDTO.builder()
            .universityName("Test University")
            .build();

        University mockUniversity = new University();

        when(universityConverter.convertToEntity(universityDTO)).thenReturn(mockUniversity);

        when(universityRepository.save(mockUniversity)).thenReturn(mockUniversity);

        UniversityDTO savedUniversityDTO = universityService.saveUniversity(universityDTO);

        assertNotNull(savedUniversityDTO);

        verify(universityConverter).convertToEntity(universityDTO);

        verify(universityRepository).save(mockUniversity);
    }

    @Test
    void shouldSaveUniversity_BlankName() {
        UniversityDTO universityDTO = UniversityDTO.builder()
            .universityName("")
            .build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            universityService.saveUniversity(universityDTO);
        });

        assertEquals("University name cannot be blank.", exception.getMessage());
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
    void shouldDeleteUniversityByName_Success() {
        UniversityDTO universityDTO = UniversityDTO.builder()
            .universityName("Test University")
            .build();

        University mockUniversity = new University();

        when(universityConverter.convertToEntity(universityDTO)).thenReturn(mockUniversity);

        boolean deleted = universityService.deleteUniversityByName(universityDTO);

        assertTrue(deleted);

        verify(universityConverter).convertToEntity(universityDTO);

        verify(universityRepository).delete(mockUniversity);
    }

    @Test
     void shouldDeleteUniversityByName_NullEntity() {
        UniversityDTO universityDTO = UniversityDTO.builder()
            .universityName("Test University")
            .build();

        when(universityConverter.convertToEntity(universityDTO)).thenReturn(null);

        boolean deleted = universityService.deleteUniversityByName(universityDTO);

        assertFalse(deleted);

        verify(universityConverter).convertToEntity(universityDTO);

        verifyNoInteractions(universityRepository);
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
