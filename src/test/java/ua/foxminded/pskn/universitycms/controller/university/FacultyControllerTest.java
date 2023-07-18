package ua.foxminded.pskn.universitycms.controller.university;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import ua.foxminded.pskn.universitycms.model.university.Faculty;
import ua.foxminded.pskn.universitycms.repository.university.FacultyRepository;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class FacultyControllerTest {

    @Mock
    private FacultyRepository facultyRepository;

    @Mock
    private Model model;

    private FacultyController facultyController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        facultyController = new FacultyController(facultyRepository);
    }

    @Test
    void facultyPage_ShouldReturnFacultyTemplate() {
        List<Faculty> facultyList = Arrays.asList(
            new Faculty(1L, 1, "Faculty 1"),
            new Faculty(2L, 1, "Faculty 2")
        );
        when(facultyRepository.findAll()).thenReturn(facultyList);

        String viewName = facultyController.facultyPage(model);

        assertEquals("faculty", viewName);
        verify(model).addAttribute("faculty", facultyList);
        verify(facultyRepository).findAll();
    }
}
