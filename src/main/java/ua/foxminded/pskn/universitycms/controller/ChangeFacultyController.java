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
public class ChangeFacultyController {

    private final UserService userService;


    @PostMapping("/studentscab/changefaculty")
    public String changeStudentFaculty(@RequestParam(name = "username", required = true) String username,
                                       @RequestParam(value = "newFacultyId") int newFacultyId,
                                       RedirectAttributes redirectAttributes
    ) {
        Optional<UserDTO> userDTO = userService.findStudentByUsername(username);
        if (userDTO.isPresent()) {
            userService.changeMyFaculty(userDTO.get(), newFacultyId);
            redirectAttributes.addFlashAttribute("successChangeFaculty", "Faculty changed successfully");
        }
        else {
            redirectAttributes.addFlashAttribute("failChangeFaculty", "Faculty couldnt changed");
        }
        return "redirect:/studentscab?username=" + username;
    }

    @PostMapping("/professorscab/changefaculty")
    public String changeProfessorFaculty(@RequestParam(name = "username", required = true) String username,
                                         @RequestParam(value = "newFacultyId") int newFacultyId,
                                         RedirectAttributes redirectAttributes
    ) {
        Optional<UserDTO> userDTO = userService.findProfessorByUsername(username);
        if (userDTO.isPresent()) {
            userService.changeMyFaculty(userDTO.get(), newFacultyId);
            redirectAttributes.addFlashAttribute("successChangeFaculty", "Faculty changed successfully");
        }
        else {
            redirectAttributes.addFlashAttribute("failChangeFaculty", "Faculty couldnt changed");
        }
        return "redirect:/professorscab?username=" + username;
    }
}
