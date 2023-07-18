package ua.foxminded.pskn.universitycms.controller.university;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import ua.foxminded.pskn.universitycms.model.university.StudentGroup;
import ua.foxminded.pskn.universitycms.repository.university.StudentGroupRepository;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class StudentGroupControllerTest {

    @Mock
    private StudentGroupRepository studentGroupRepository;

    @Mock
    private Model model;

    private StudentGroupController studentGroupController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        studentGroupController = new StudentGroupController(studentGroupRepository);
    }

    @Test
    void studentGroupPage_ShouldReturnStudentGroupTemplate() {
        List<StudentGroup> studentGroupList = Arrays.asList(
            new StudentGroup(1L, 1, "Group 1"),
            new StudentGroup(2L, 1, "Group 2")
        );
        when(studentGroupRepository.findAll()).thenReturn(studentGroupList);

        String viewName = studentGroupController.studentGroupPage(model);

        assertEquals("studentgroup", viewName);
        verify(model).addAttribute("student_group", studentGroupList);
        verify(studentGroupRepository).findAll();
    }
}
