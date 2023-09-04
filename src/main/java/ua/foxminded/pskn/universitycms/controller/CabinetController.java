package ua.foxminded.pskn.universitycms.controller;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.foxminded.pskn.universitycms.model.user.User;
import ua.foxminded.pskn.universitycms.model.usercabinetdata.StudentCabinetData;
import ua.foxminded.pskn.universitycms.service.user.UserService;
import ua.foxminded.pskn.universitycms.service.usercabinet.UserCabinetService;

import java.util.Optional;


@Controller
@RequiredArgsConstructor
public class CabinetController {

    private final UserService userService;

    private final UserCabinetService userCabinetService;

    @GetMapping("/professorscab")
    public String professorCabinetPage(@RequestParam(name = "username", required = false) String name, Model model) {
        if (StringUtils.isNotBlank(name)) {
            Optional<User> professor = userService.findProfessorByUsername(name);
            professor.ifPresent(p -> model.addAttribute("username", p.getUsername()));
        } else{
            throw new IllegalArgumentException("Username cannot be blank");
        }
        return "professorscab";
    }

    @GetMapping("/adminscab")
    public String adminCabinetPage(@RequestParam(name = "username", required = false) String name, Model model) {
        if (StringUtils.isNotBlank(name)) {
            Optional<User> admin = userService.findAdminByUsername(name);
            admin.ifPresent(a -> model.addAttribute("username", a.getUsername()));
        } else {
            throw new IllegalArgumentException("Username cannot be blank");
        }
        return "adminscab";
    }

    @GetMapping("/studentscab")
    public String studentCabinetPage(@RequestParam(name = "username", required = false) String name, Model model) {
        StudentCabinetData cabinetData = userCabinetService.getStudentCabinetData(name);


        if (cabinetData != null) {
            model.addAttribute("username", cabinetData.getUsername());
            model.addAttribute("studentId", cabinetData.getStudentId());
            model.addAttribute("studentGroup", cabinetData.getStudentGroup());
            model.addAttribute("availableGroups", cabinetData.getAvailableGroups());
        } else {
            throw new IllegalArgumentException("Cabinet data cannot be blank");
        }
        return "studentscabview";
    }
}
