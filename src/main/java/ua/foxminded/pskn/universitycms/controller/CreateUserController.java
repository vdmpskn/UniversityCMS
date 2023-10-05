package ua.foxminded.pskn.universitycms.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ua.foxminded.pskn.universitycms.converter.role.RoleConverter;
import ua.foxminded.pskn.universitycms.customexception.UserCreateException;
import ua.foxminded.pskn.universitycms.dto.RoleDTO;
import ua.foxminded.pskn.universitycms.dto.UserDTO;
import ua.foxminded.pskn.universitycms.model.user.Role;
import ua.foxminded.pskn.universitycms.service.user.RoleService;
import ua.foxminded.pskn.universitycms.service.user.UserService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequiredArgsConstructor
public class CreateUserController {

    private final UserService userService;

    private final RoleConverter roleConverter;

    private final RoleService roleService;

    @GetMapping("/adminscab/create-user")
    public String showCreateUserForm(Model model) {

        List<RoleDTO> roles = roleService.getAllRoles();

        model.addAttribute("userForm", new UserDTO());
        model.addAttribute("roles", roles);
        return "redirect:/adminscab";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/adminscab/create-user")
    public String createUser(@ModelAttribute("userForm") UserDTO userDTO, @RequestParam("roleId") int roleId, int groupId, RedirectAttributes redirectAttributes) {
        try {

            userService.createUserWithRole(userDTO, groupId, roleId);
            redirectAttributes.addFlashAttribute("successMessage", "User created successfully");
        }
        catch (UserCreateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to create user: " + e.getMessage());
        }
        return "redirect:/adminscab";
    }
}
