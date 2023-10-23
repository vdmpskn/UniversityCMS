package ua.foxminded.pskn.universitycms.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.foxminded.pskn.universitycms.service.user.StudentService;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ChangeGroupControllerTest {

    @InjectMocks
    private ChangeGroupController changeGroupController;

    @Mock
    private StudentService studentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldChangeStudentGroup() {
        Principal principal = () -> "testuser";

        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        String result = changeGroupController.changeStudentGroup(1L, 2L, redirectAttributes, principal);

        assertEquals("redirect:/studentscab?username=testuser", result);

        verify(studentService).changeMyGroup(1L, 2L);

        verify(redirectAttributes).addFlashAttribute("successMessage", "Student group changed successfully to 2");
    }
}

