package ua.foxminded.pskn.universitycms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.foxminded.pskn.universitycms.model.user.Role;
import ua.foxminded.pskn.universitycms.model.user.User;
import ua.foxminded.pskn.universitycms.service.user.RoleService;
import ua.foxminded.pskn.universitycms.service.user.UserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CreateUserController {

    private final UserService userService;

    private final RoleService roleService;

    @GetMapping("/adminscab/create-user")
    public String showCreateUserForm(Model model) {
        List<Role> roles = roleService.getAllRoles();

        model.addAttribute("userForm", new User());
        model.addAttribute("roles", roles);
        return "redirect:/adminscab";
    }

    @PreAuthorize("hasAuthority('ROLE_1')")
    @PostMapping("/adminscab/create-user")
    public String createUser(@ModelAttribute("userForm") User user, int groupId, RedirectAttributes redirectAttributes) {
        try {
            userService.createUserWithRole(user, groupId);
            redirectAttributes.addFlashAttribute("successMessage", "User created successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to create user: " + e.getMessage());
        }
        return "redirect:/adminscab";
    }
}
