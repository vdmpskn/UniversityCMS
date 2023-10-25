package ua.foxminded.pskn.universitycms.controller.user;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ua.foxminded.pskn.universitycms.dto.RoleDTO;
import ua.foxminded.pskn.universitycms.dto.UserDTO;
import ua.foxminded.pskn.universitycms.service.user.RoleService;
import ua.foxminded.pskn.universitycms.service.user.UserService;

@WebMvcTest(UserController.class)
@WithMockUser(authorities = "ROLE_ADMIN")
class UserControllerTest {

    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    @MockBean
    private RoleService roleService;

    @MockBean
    private Model model;

    @Test
    void shouldGetUserPage() {
        int page = 0;
        int pageSize = 5;
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<UserDTO> mockUserPage = mock(Page.class);

        List<UserDTO> mockUsers = new ArrayList<>();
        mockUsers.add(new UserDTO());
        mockUsers.add(new UserDTO());

        List<RoleDTO> mockRoles = new ArrayList<>();
        mockRoles.add(new RoleDTO());
        mockRoles.add(new RoleDTO());

        when(userService.getAllUsers(pageable)).thenReturn(mockUserPage);
        when(mockUserPage.getContent()).thenReturn(mockUsers);
        when(mockUserPage.getTotalPages()).thenReturn(3);
        when(roleService.getAllRoles()).thenReturn(mockRoles);

        String viewName = userController.userPage(model, page, pageSize);

        assertEquals("/users/user", viewName);
        verify(model).addAttribute("users", mockUserPage.getContent());
        verify(model).addAttribute("currentPage", page);
        verify(model).addAttribute("totalPages", mockUserPage.getTotalPages());
        verify(model).addAttribute("userDTO", new UserDTO());
        verify(model).addAttribute("roles", mockRoles);
    }

    @Test
    void shouldDeleteUser_Successful() {
        UserService userService = mock(UserService.class);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        ReflectionTestUtils.setField(userController, "userService", userService);

        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(1L);
        doNothing().when(userService).deleteUser(userDTO);

        String result = userController.deleteUser(userDTO, redirectAttributes);
        assertEquals("redirect:/user", result);
        verify(redirectAttributes).addFlashAttribute(eq("successDeleteMessage"), anyString());
    }

    @Test
    void shouldEditUser_Successful() {
        UserService userService = mock(UserService.class);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        ReflectionTestUtils.setField(userController, "userService", userService);

        UserDTO userDTO = new UserDTO();
        Long roleId = 2L;
        userDTO.setUserId(1L);

        doNothing().when(userService).updateUser(userDTO, roleId);

        String result = userController.editUser(userDTO, roleId, redirectAttributes);

        assertEquals("redirect:/user", result);

        verify(redirectAttributes).addFlashAttribute(eq("successEditMessage"), anyString());
    }

}
