package ua.foxminded.pskn.universitycms.controller.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import ua.foxminded.pskn.universitycms.model.user.Student;
import ua.foxminded.pskn.universitycms.repository.user.StudentRepository;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class StudentControllerTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private Model model;

    private StudentController studentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        studentController = new StudentController(studentRepository);
    }

    @Test
    void studentPage_ShouldReturnStudentsTemplate() {
        List<Student> studentList = Arrays.asList(
            new Student(1L, 2),
            new Student(2L, 2)
        );
        when(studentRepository.findAll()).thenReturn(studentList);

        String viewName = studentController.studentPage(model);

        assertEquals("students", viewName);
        verify(model).addAttribute("students", studentList);
        verify(studentRepository).findAll();
    }
}

