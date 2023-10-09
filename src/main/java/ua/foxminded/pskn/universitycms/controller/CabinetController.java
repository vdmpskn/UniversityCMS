package ua.foxminded.pskn.universitycms.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import ua.foxminded.pskn.universitycms.customexception.ProfessorNotFoundException;
import ua.foxminded.pskn.universitycms.dto.UserDTO;
import ua.foxminded.pskn.universitycms.model.user.User;
import ua.foxminded.pskn.universitycms.model.usercabinetdata.StudentCabinetData;
import ua.foxminded.pskn.universitycms.service.user.UserService;
import ua.foxminded.pskn.universitycms.service.usercabinet.UserCabinetService;


@Controller
@RequiredArgsConstructor
public class CabinetController {

    private final UserService userService;

    private final UserCabinetService userCabinetService;

    @GetMapping("/professorscab")
    public String professorCabinetPage(@RequestParam(name = "username", required = true) String name, Model model) {

        Optional<UserDTO> professor = userService.findProfessorByUsername(name);

        if(professor.isEmpty()){
            throw new ProfessorNotFoundException("Professor not found.");
        }
        model.addAttribute("username", professor.get().getUsername());
        return "professorscab";
    }

    @GetMapping("/adminscab")
    public String adminCabinetPage() {

        return "adminscab";
    }

    @GetMapping("/studentscab")
    public String studentCabinetPage(@RequestParam(name = "username", required = true) String name, Model model) {
        StudentCabinetData cabinetData = userCabinetService.getStudentCabinetData(name);

        model.addAttribute("username", cabinetData.getUsername());
        model.addAttribute("studentId", cabinetData.getStudentId());
        model.addAttribute("studentGroup", cabinetData.getStudentGroup());
        model.addAttribute("availableGroups", cabinetData.getAvailableGroups());

        return "studentscab";
    }
}
