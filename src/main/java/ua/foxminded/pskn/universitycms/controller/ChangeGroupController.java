package ua.foxminded.pskn.universitycms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import ua.foxminded.pskn.universitycms.service.user.StudentService;


@Controller
@RequiredArgsConstructor
public class ChangeGroupController {

    private final StudentService studentService;

    @PostMapping("/studentscab/changeGroup")
    public String changeStudentGroup(@RequestParam Long studentId,
                                     @RequestParam Long groupId,
                                     RedirectAttributes redirectAttributes) {
        studentService.changeMyGroup(studentId, groupId);
        String successMessage = "Student group changed successfully to " + groupId;
        redirectAttributes.addFlashAttribute("successMessage", successMessage);
        return "redirect:/studentscab";
    }
}
