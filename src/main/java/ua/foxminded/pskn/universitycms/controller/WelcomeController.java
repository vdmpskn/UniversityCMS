package ua.foxminded.pskn.universitycms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.foxminded.pskn.universitycms.model.user.User;
import ua.foxminded.pskn.universitycms.service.user.UserService;
import java.util.List;

@Controller
@RequestMapping("/")
public class WelcomeController {

    private final UserService userService;

    public WelcomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String welcomePage(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "welcome";
    }
}
