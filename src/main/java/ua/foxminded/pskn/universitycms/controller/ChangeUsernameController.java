package ua.foxminded.pskn.universitycms.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import ua.foxminded.pskn.universitycms.dto.UserDTO;
import ua.foxminded.pskn.universitycms.service.user.UserService;

@Controller
@RequiredArgsConstructor
public class ChangeUsernameController {


    private final UserService userService;

    @PostMapping("/studentscab/changeusername")
    public String changeStudentUsername(@RequestParam(name = "username", required = true) String username,
                                        @RequestParam(name = "newUsername", required = true) String newUsername,
                                        RedirectAttributes redirectAttributes) {
        Optional<UserDTO> studentDTO = userService.findStudentByUsername(username);
        if (studentDTO.isPresent()) {
            userService.changeMyName(studentDTO.get(), newUsername);
            redirectAttributes.addFlashAttribute("successfulChangeStudentName", "Your username changed successfully");
        }
        else {
            redirectAttributes.addFlashAttribute("studentIsEmpty", "Student is empty!");
        }
        return "redirect:/studentscab?username=" + newUsername;
    }

    @PostMapping("/professorscab/changeusername")
    public String changeProfessorUsername(@RequestParam(name = "username", required = true) String username,
                                          @RequestParam(name = "newUsername", required = true) String newUsername,
                                          RedirectAttributes redirectAttributes) {
        Optional<UserDTO> professorDTO = userService.findProfessorByUsername(username);
        if (professorDTO.isPresent()) {
            userService.changeMyName(professorDTO.get(), newUsername);
            redirectAttributes.addFlashAttribute("successfulChangeProfessorName", "Your username changed successfully");
        }
        else {
            redirectAttributes.addFlashAttribute("professorIsEmpty", "Professor is empty!");
        }

        return "redirect:/professorscab?username=" + newUsername;
    }

}
