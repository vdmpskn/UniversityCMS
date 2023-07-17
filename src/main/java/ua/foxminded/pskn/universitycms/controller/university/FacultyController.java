package ua.foxminded.pskn.universitycms.controller.university;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.foxminded.pskn.universitycms.model.university.Faculty;
import ua.foxminded.pskn.universitycms.repository.university.FacultyRepository;

import java.util.List;

@Controller
@RequestMapping("/faculty.html")
public class FacultyController {
    private final FacultyRepository facultyRepository;

    public FacultyController(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @GetMapping
    public String facultyPage(Model model) {
        List<Faculty> faculty = facultyRepository.findAll();
        model.addAttribute("faculty", faculty);
        return "faculty";
    }
}
