package ua.foxminded.pskn.universitycms.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ua.foxminded.pskn.universitycms.customexception.UserUpdateException;
import ua.foxminded.pskn.universitycms.dto.RoleDTO;
import ua.foxminded.pskn.universitycms.dto.UserDTO;
import ua.foxminded.pskn.universitycms.model.user.Role;
import ua.foxminded.pskn.universitycms.model.user.User;
import ua.foxminded.pskn.universitycms.service.user.RoleService;
import ua.foxminded.pskn.universitycms.service.user.UserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final RoleService roleService;

    @GetMapping
    public String userPage(Model model,
                           @RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "5") int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<UserDTO> userPage = userService.getAllUsers(pageable);
        List<RoleDTO> roles = roleService.getAllRoles();


        model.addAttribute("users", userPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", userPage.getTotalPages());

        model.addAttribute("userDTO", new UserDTO());

        model.addAttribute("roles", roles);

        return "/users/user";
    }

    @PostMapping("/delete")
    public String deleteUser(@ModelAttribute("userDTO") UserDTO userDTO,
                             RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUser(userDTO);
            redirectAttributes.addFlashAttribute("successDeleteMessage", "User deleted successfully");
        } catch (UserUpdateException e) {
            redirectAttributes.addFlashAttribute("errorDeleteMessage", "Failed to delete user: " + e.getMessage());
        }
        return "redirect:/user";
    }

    @PostMapping("/edit")
    public String editUser(@ModelAttribute("userDTO") UserDTO userDTO,
                           @RequestParam("roleId") int roleId,
                           RedirectAttributes redirectAttributes) {
        try {
            userService.updateUser(userDTO, roleId);
            redirectAttributes.addFlashAttribute("successEditMessage", "User updated successfully");
        } catch (UserUpdateException e) {
            redirectAttributes.addFlashAttribute("errorEditMessage", "Failed to update user: " + e.getMessage());
        }
        return "redirect:/user";
    }
}
