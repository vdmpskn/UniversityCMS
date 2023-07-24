package ua.foxminded.pskn.universitycms.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ua.foxminded.pskn.universitycms.model.user.User;
import ua.foxminded.pskn.universitycms.service.user.UserService;


@Controller
public class CabController {

    private final UserService userService;

    public CabController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/cabinet")
    public String redirect() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getPrincipal();
        User user = userService.getUserByUsername(authentication.getName());
        if (user.getRole().equals("professor")) {
            return "redirect:/professorscab";
        }
        if (user.getRole().equals("student")) {
            return "redirect:/studentscab";
        }
        if (user.getRole().equals("admin")) {
            return "redirect:/adminscab";
        }
        return "";

    }

    @GetMapping("/professorscab")
    public String professorCabinetPage() {
        return "/professorscab";
    }

    @GetMapping("/adminscab")
    public String adminCabinetPage() {
        return "/adminscab";
    }

    @GetMapping("/studentscab")
    public String studentCabinetPage() {
        return "/studentscab";
    }


}
