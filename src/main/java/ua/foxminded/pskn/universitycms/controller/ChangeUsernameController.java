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
public class ChangeUsernameController {


    private final UserService userService;

    @PostMapping("/studentscab/changeusername")
    public String changeStudentUsername(@RequestParam(name = "userId") Long userId,
                                        @RequestParam(name = "newUsername", required = true) String newUsername,
                                        RedirectAttributes redirectAttributes) {

        boolean changedUsername = userService.changeUsername(userId, newUsername);
        if (changedUsername) {
            redirectAttributes.addFlashAttribute("successfulChangeStudentName", "Your username changed successfully! Login with new username.");
        } else {
            redirectAttributes.addFlashAttribute("studentIsEmpty", "Student is empty!");
        }
        return "redirect:/login";
    }

    @PostMapping("/professorscab/changeusername")
    public String changeProfessorUsername(@RequestParam(name = "userId") Long userId,
                                          @RequestParam(name = "newUsername", required = true) String newUsername,
                                          RedirectAttributes redirectAttributes) {
        boolean changedUsername = userService.changeUsername(userId, newUsername);

        if (changedUsername) {
            redirectAttributes.addAttribute("successfulChangeProfessorName", "Your username changed successfully! Login with new username.");
        } else {
            redirectAttributes.addFlashAttribute("professorIsEmpty", "Professor is empty!");
        }
        return "redirect:/login";
    }

}
