package ua.foxminded.pskn.universitycms.controller;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.foxminded.pskn.universitycms.model.user.User;
import ua.foxminded.pskn.universitycms.service.user.UserService;


@Controller
@RequiredArgsConstructor
public class CabinetController {

    private final UserService userService;

    @GetMapping("/professorscab")
    public String professorCabinetPage(@RequestParam(name = "username", required = false) String name, Model model) {
        if (StringUtils.isNotBlank(name)) {
            User professor = userService.findProfessorByUsername(name);
            model.addAttribute("username", professor.getUsername());
        }
        return "professorscab";
    }

    @GetMapping("/adminscab")
    public String adminCabinetPage(@RequestParam(name="username", required=false) String name, Model model) {
        if (StringUtils.isNotBlank(name)) {
            User admin = userService.findAdminByUsername(name);
            model.addAttribute("username", admin.getUsername());
        }
        return "adminscab";
    }

    @GetMapping("/studentscab")
    public String studentCabinetPage(@RequestParam(name="username", required=false) String name, Model model) {
        if (StringUtils.isNotBlank(name)) {
            User student = userService.findStudentByUsername(name);
            model.addAttribute("username", student.getUsername());
        }
        return "studentscab";
    }


}
