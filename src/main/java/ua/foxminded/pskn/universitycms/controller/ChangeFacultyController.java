package ua.foxminded.pskn.universitycms.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import ua.foxminded.pskn.universitycms.service.user.UserService;

@Controller
@RequiredArgsConstructor
public class ChangeFacultyController {

    private final UserService userService;


    @PostMapping("/studentscab/changefaculty")
    public String changeStudentFaculty(@RequestParam(name = "userId") Long userId,
                                       @RequestParam(value = "newFacultyId") int newFacultyId,
                                       RedirectAttributes redirectAttributes
    ) {
        boolean facultyChanged = userService.changeStudentFaculty(userId, newFacultyId);

        if (facultyChanged) {
            redirectAttributes.addFlashAttribute("successChangeFaculty", "Faculty changed successfully");
        } else {
            redirectAttributes.addFlashAttribute("failChangeFaculty", "Faculty couldn't be changed");
        }
        return "redirect:/studentscab";
    }

    @PostMapping("/professorscab/changefaculty")
    public String changeProfessorFaculty(@RequestParam(name = "userId") Long userId,
                                         @RequestParam(value = "newFacultyId") int newFacultyId,
                                         RedirectAttributes redirectAttributes
    ) {
        boolean facultyChanged = userService.changeProfessorFaculty(userId, newFacultyId);

        if (facultyChanged) {
            redirectAttributes.addFlashAttribute("successChangeFaculty", "Faculty changed successfully");
        } else {
            redirectAttributes.addFlashAttribute("failChangeFaculty", "Faculty couldn't be changed");
        }
        return "redirect:/professorscab";
    }
}
