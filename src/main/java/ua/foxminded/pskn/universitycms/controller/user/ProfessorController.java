package ua.foxminded.pskn.universitycms.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.foxminded.pskn.universitycms.model.user.Professor;
import ua.foxminded.pskn.universitycms.repository.user.ProfessorRepository;

import java.util.List;

@Controller
@RequestMapping("/professors.html")
public class ProfessorController {
    private final ProfessorRepository professorRepository;

    public ProfessorController(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    @GetMapping
    public String professorPage(Model model) {
        List<Professor> professors = professorRepository.findAll();
        model.addAttribute("professors", professors);
        return "professors";
    }
}
