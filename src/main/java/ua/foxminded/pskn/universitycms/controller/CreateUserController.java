package ua.foxminded.pskn.universitycms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.foxminded.pskn.universitycms.model.user.User;
import ua.foxminded.pskn.universitycms.service.user.UserService;

import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CreateUserController {

    private final UserService userService;

    @GetMapping("/adminscab/create-user")
    public String showCreateUserForm(Model model) {
        List<String> roles = Arrays.asList("admin", "student", "professor");

        model.addAttribute("userForm", new User());
        model.addAttribute("roles", roles);
        return "redirect:/adminscab";
    }

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("/adminscab/create-user")
    public String createUser(@ModelAttribute("userForm") User user, int groupId) {
        if (user.getRole().equals("admin")){
            userService.saveAdmin(user);
        }
        if (user.getRole().equals("student")){
            userService.saveStudent(user, groupId);
        }
        if (user.getRole().equals("professor")){
            userService.saveProfessor(user);
        }

        return "redirect:/adminscab";
    }

}
