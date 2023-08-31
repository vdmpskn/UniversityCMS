package ua.foxminded.pskn.universitycms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.foxminded.pskn.universitycms.service.user.StudentService;

import java.security.Principal;


@Controller
@RequiredArgsConstructor
public class ChangeGroupController {

    private final StudentService studentService;

    @PostMapping("/studentscab/changeGroup")
    public String changeStudentGroup(@RequestParam Long studentId, @RequestParam int groupId, RedirectAttributes redirectAttributes, Principal principal) {
        String username = principal.getName();
        studentService.changeMyGroup(studentId, groupId);
        String successMessage = "Student group changed successfully to " + groupId;
        redirectAttributes.addFlashAttribute("successMessage", successMessage);
        return "redirect:/studentscab?username=" + username;
    }
}
