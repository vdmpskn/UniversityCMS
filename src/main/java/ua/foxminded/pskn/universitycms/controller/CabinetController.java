package ua.foxminded.pskn.universitycms.controller;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.foxminded.pskn.universitycms.model.university.StudentGroup;
import ua.foxminded.pskn.universitycms.model.user.Student;
import ua.foxminded.pskn.universitycms.model.user.User;
import ua.foxminded.pskn.universitycms.service.university.StudentGroupService;
import ua.foxminded.pskn.universitycms.service.user.StudentService;
import ua.foxminded.pskn.universitycms.service.user.UserService;

import java.util.List;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
public class CabinetController {

    private final UserService userService;

    private final StudentService studentService;

    private final StudentGroupService studentGroupService;

    @GetMapping("/professorscab")
    public String professorCabinetPage(@RequestParam(name = "username", required = false) String name, Model model) {
        if (StringUtils.isNotBlank(name)) {
            Optional<User> professor = userService.findProfessorByUsername(name);
            model.addAttribute("username", professor.get().getUsername());
        }
        return "professorscab";
    }

    @GetMapping("/adminscab")
    public String adminCabinetPage(@RequestParam(name="username", required=false) String name, Model model) {
        if (StringUtils.isNotBlank(name)) {
            Optional<User> admin = userService.findAdminByUsername(name);
            model.addAttribute("username", admin.get().getUsername());
        }
        return "adminscab";
    }

    @GetMapping("/studentscab")
    public String studentCabinetPage(@RequestParam(name="username", required=false) String name, Model model) {
        if (StringUtils.isNotBlank(name)) {
            Optional<User> student = userService.findStudentByUsername(name);
            Optional<Student> studentForGroup = studentService.getStudentByUserId(student.get().getUserId());
            StudentGroup groupName = studentGroupService.getStudentGroupById((long)studentForGroup.get().getGroupId());
            List<StudentGroup> availableGroups = studentGroupService.getAllStudentGroups(); // Получите список всех доступных групп из сервиса
            model.addAttribute("availableGroups", availableGroups);

            model.addAttribute("username", student.get().getUsername());
            model.addAttribute("studentId", studentForGroup.get().getUserId());
            model.addAttribute("studentGroup", groupName.getGroupName());
        }
        return "studentscab";
    }

}
