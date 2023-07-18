package ua.foxminded.pskn.universitycms.controller.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import ua.foxminded.pskn.universitycms.model.user.User;
import ua.foxminded.pskn.universitycms.service.user.UserService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userController = new UserController(userService);
    }

    @Test
    void userPage_ShouldReturnUserTemplate() {
        List<User> userList = Arrays.asList(
            new User(1L, "username", "pass", "student", 1),
            new User(2L, "user", "admin", "admin", 1)
        );
        when(userService.getAllUsers()).thenReturn(userList);

        String viewName = userController.userPage(model);

        assertEquals("user", viewName);
        verify(model).addAttribute("users", userList);
        verify(userService).getAllUsers();
    }
}
