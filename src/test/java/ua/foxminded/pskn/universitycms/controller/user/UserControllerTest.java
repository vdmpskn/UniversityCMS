package ua.foxminded.pskn.universitycms.controller.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;
import ua.foxminded.pskn.universitycms.model.user.User;
import ua.foxminded.pskn.universitycms.service.user.UserService;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void testUserPage() throws Exception {
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
}
