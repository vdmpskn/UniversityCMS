package ua.foxminded.pskn.universitycms.controller.university;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.foxminded.pskn.universitycms.model.university.University;
import ua.foxminded.pskn.universitycms.repository.university.UniversityRepository;

import java.util.List;

@Controller
@RequestMapping("/university.html")
public class UniversityController {
    private final UniversityRepository universityRepository;


    public UniversityController(UniversityRepository universityRepository) {
        this.universityRepository = universityRepository;
    }

    @GetMapping
    public String universityyPage(Model model) {
        List<University> universities = universityRepository.findAll();
        model.addAttribute("university", universities);
        return "university";
    }
}
