package ua.foxminded.pskn.universitycms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.foxminded.pskn.universitycms.model.user.User;
import ua.foxminded.pskn.universitycms.service.user.UserService;

import java.util.List;


@Controller
public class CabinetController {

    private final UserService userService;

    public CabinetController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/professorscab")
    public String professorCabinetPage(@RequestParam(name="username", required=false) String name, Model model) {
        if (name != null) {
            List<User> userList = userService.getAllUsers();
            User professor = userList.stream()
                .filter(user -> user.getUsername().equals(name))
                .findFirst()
                .orElse(null);
            if (professor != null) {
                model.addAttribute("username", professor.getUsername());
            }
        }
        return "professorscab";
    }

    @GetMapping("/adminscab")
    public String adminCabinetPage(@RequestParam(name="username", required=false) String name, Model model) {
        if (name != null) {
            List<User> userList = userService.getAllUsers();
            User admin = userList.stream()
                .filter(user -> user.getUsername().equals(name))
                .findFirst()
                .orElse(null);
            if (admin != null) {
                model.addAttribute("username", admin.getUsername());
            }
        }
        return "adminscab";
    }

    @GetMapping("/studentscab")
    public String studentCabinetPage(@RequestParam(name="username", required=false) String name, Model model) {
        if (name != null) {
            List<User> userList = userService.getAllUsers();
            User student = userList.stream()
                .filter(user -> user.getUsername().equals(name))
                .findFirst()
                .orElse(null);
            if (student != null) {
                model.addAttribute("username", student.getUsername());
            }
        }
        return "studentscab";
    }


}
