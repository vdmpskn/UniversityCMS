package ua.foxminded.pskn.universitycms.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ua.foxminded.pskn.universitycms.dto.RoleDTO;
import ua.foxminded.pskn.universitycms.dto.UserDTO;
import ua.foxminded.pskn.universitycms.model.user.Role;
import ua.foxminded.pskn.universitycms.service.user.RoleService;
import ua.foxminded.pskn.universitycms.service.user.UserService;

@WebMvcTest(CreateUserController.class)
@WithMockUser(authorities = "ROLE_ADMIN")
class CreateUserControllerTest {

    @Autowired
    private CreateUserController createUserController;

    @MockBean
    private UserService userService;

    @MockBean
    private RoleService roleService;

    @Test
    void shouldCreateUser_AdminAuthorization() {
        UserDTO userDTO = new UserDTO();
        userDTO.builder().roleDTO(RoleDTO.builder().roleName("testrole").role(Role.builder().roleId(2).name("testrole").build()).build());
        Long groupId = 1L;
        Long roleId = 2L;
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        doNothing().when(userService).createUserWithRole(userDTO, groupId, roleId);

        String result = createUserController.createUser(userDTO, groupId, roleId, redirectAttributes);

        assertEquals("redirect:/adminscab", result);

        verify(redirectAttributes).addFlashAttribute(eq("successMessage"), anyString());
    }

}
