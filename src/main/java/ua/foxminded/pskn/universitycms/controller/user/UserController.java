package ua.foxminded.pskn.universitycms.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.foxminded.pskn.universitycms.dto.FacultyDTO;
import ua.foxminded.pskn.universitycms.dto.UserDTO;
import ua.foxminded.pskn.universitycms.model.user.User;
import ua.foxminded.pskn.universitycms.service.user.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public String userPage(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<User> userPage = userService.getAllUsers(pageable);
        List<String> roles = Arrays.asList("admin", "student", "professor");


        model.addAttribute("users", userPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", userPage.getTotalPages());

        model.addAttribute("userDTO", new UserDTO());

        model.addAttribute("roles", roles);

        return "/users/user";
    }

    @PostMapping("/delete")
    public String deleteUser(@ModelAttribute("userDTO") UserDTO userDTO) {
           userService.deleteUser(userDTO);
        return "redirect:/user";
    }

    @PostMapping("/edit")
    public String editUser(@ModelAttribute("userDTO") UserDTO userDTO, RedirectAttributes redirectAttributes) {

        userService.updateUser(userDTO);

        return "redirect:/user";
    }
}
