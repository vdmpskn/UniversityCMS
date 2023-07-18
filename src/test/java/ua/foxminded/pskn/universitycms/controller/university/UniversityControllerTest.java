package ua.foxminded.pskn.universitycms.controller.university;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import ua.foxminded.pskn.universitycms.model.university.University;
import ua.foxminded.pskn.universitycms.repository.university.UniversityRepository;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UniversityControllerTest {

    @Mock
    private UniversityRepository universityRepository;

    @Mock
    private Model model;

    private UniversityController universityController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        universityController = new UniversityController(universityRepository);
    }

    @Test
    void universityPage_ShouldReturnUniversityTemplate() {
        List<University> universityList = Arrays.asList(
            new University(1L, "University 1"),
            new University(2L, "University 2")
        );
        when(universityRepository.findAll()).thenReturn(universityList);

        String viewName = universityController.universityPage(model);

        assertEquals("university", viewName);
        verify(model).addAttribute("university", universityList);
        verify(universityRepository).findAll();
    }
}

