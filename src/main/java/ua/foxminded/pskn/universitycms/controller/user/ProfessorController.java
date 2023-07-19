package ua.foxminded.pskn.universitycms.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.foxminded.pskn.universitycms.model.user.Professor;
import ua.foxminded.pskn.universitycms.service.user.ProfessorService;


@Controller
@RequiredArgsConstructor
@RequestMapping("/professors")
public class ProfessorController {

    private final ProfessorService professorService;

    @GetMapping
    public String professorPage(Model model, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Professor> professorPage = professorService.getAllProfessors(pageable);

        model.addAttribute("professors", professorPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", professorPage.getTotalPages());
        return "/users/professors";
    }
}
