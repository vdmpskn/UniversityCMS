package ua.foxminded.pskn.universitycms.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.foxminded.pskn.universitycms.model.user.User;
import ua.foxminded.pskn.universitycms.service.user.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@WebMvcTest(CreateUserController.class)
@WithMockUser(authorities = "ROLE_ADMIN")
class CreateUserControllerTest {

    @Autowired
    private CreateUserController createUserController;

    @MockBean
    private UserService userService;

    @Test
    void shouldCreateUser_AdminAuthorization() {
        User user = new User();
        user.setRoleId(1);
        int groupId = 1;
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        doNothing().when(userService).createUserWithRole(user, groupId);

        String result = createUserController.createUser(user, groupId, redirectAttributes);

        assertEquals("redirect:/adminscab", result);

        verify(redirectAttributes).addFlashAttribute(eq("successMessage"), anyString());
    }

}