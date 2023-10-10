package ua.foxminded.pskn.universitycms.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import ua.foxminded.pskn.universitycms.customexception.ProfessorNotFoundException;
import ua.foxminded.pskn.universitycms.dto.FacultyDTO;
import ua.foxminded.pskn.universitycms.dto.UserDTO;
import ua.foxminded.pskn.universitycms.model.usercabinetdata.StudentCabinetData;
import ua.foxminded.pskn.universitycms.service.university.FacultyService;
import ua.foxminded.pskn.universitycms.service.user.UserService;
import ua.foxminded.pskn.universitycms.service.usercabinet.UserCabinetService;


@Controller
@RequiredArgsConstructor
public class CabinetController {

    private final UserService userService;

    private final UserCabinetService userCabinetService;

    private final FacultyService facultyService;

    @GetMapping("/professorscab")
    public String professorCabinetPage(@RequestParam(name = "username", required = true) String name, Model model) {

        Optional<UserDTO> professor = userService.findProfessorByUsername(name);

        if(professor.isEmpty()){
            throw new ProfessorNotFoundException("Professor not found.");
        }
        List<FacultyDTO> facultyDTOList = facultyService.findAll();
        model.addAttribute("professor", professor.get());
        model.addAttribute("username", professor.get().getUsername());
        model.addAttribute("availableFaculties", facultyDTOList);
        return "professorscab";
    }

    @GetMapping("/adminscab")
    public String adminCabinetPage() {

        return "adminscab";
    }

    @GetMapping("/studentscab")
    public String studentCabinetPage(@RequestParam(name = "username", required = true) String name, Model model) {
        StudentCabinetData cabinetData = userCabinetService.getStudentCabinetData(name);

        List<FacultyDTO> facultyDTOList = facultyService.findAll();

        model.addAttribute("username", cabinetData.getUsername());
        model.addAttribute("studentId", cabinetData.getStudentId());
        model.addAttribute("studentGroup", cabinetData.getStudentGroup());
        model.addAttribute("availableGroups", cabinetData.getAvailableGroups());
        model.addAttribute("availableFaculties", facultyDTOList);

        return "studentscab";
    }
}
