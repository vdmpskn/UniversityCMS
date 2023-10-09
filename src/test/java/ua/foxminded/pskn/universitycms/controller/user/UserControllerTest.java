package ua.foxminded.pskn.universitycms.controller.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.foxminded.pskn.universitycms.dto.UserDTO;
import ua.foxminded.pskn.universitycms.model.user.User;
import ua.foxminded.pskn.universitycms.service.user.RoleService;
import ua.foxminded.pskn.universitycms.service.user.UserService;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@WithMockUser(authorities = "ROLE_ADMIN")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    @MockBean
    private RoleService roleService;

    @Test
    void shouldGetUserPage() throws Exception {
        User user = new User();
        user.setUserId(1L);
        user.setUsername("John Doe");

        Page<User> userPage = new PageImpl<>(Collections.singletonList(user));
        when(userService.getAllUsers(any())).thenReturn(userPage);

        mockMvc.perform(get("/user"))
            .andExpect(status().isOk())
            .andExpect(view().name("/users/user"))
            .andExpect(model().attributeExists("users"))
            .andExpect(model().attribute("users", userPage.getContent()))
            .andExpect(model().attributeExists("currentPage"))
            .andExpect(model().attribute("currentPage", 0))
            .andExpect(model().attributeExists("totalPages"))
            .andExpect(model().attribute("totalPages", userPage.getTotalPages()));
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
        userDTO.setUserId(1L);

        doNothing().when(userService).updateUser(userDTO);

        String result = userController.editUser(userDTO, redirectAttributes);

        assertEquals("redirect:/user", result);

        verify(redirectAttributes).addFlashAttribute(eq("successEditMessage"), anyString());
    }

}
