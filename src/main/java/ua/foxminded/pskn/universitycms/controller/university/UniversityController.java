package ua.foxminded.pskn.universitycms.controller.university;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.foxminded.pskn.universitycms.model.university.University;
import ua.foxminded.pskn.universitycms.service.university.UniversityService;

@Controller
@RequestMapping("/university")
public class UniversityController {
    private final UniversityService universityService;

    public UniversityController(UniversityService universityService) {
        this.universityService = universityService;
    }

    @GetMapping
    public String universityPage(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<University> universityPage = universityService.getAllUniversities(pageable);
        model.addAttribute("university", universityPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", universityPage.getTotalPages());
        return "university/university";
    }

    @PostMapping("/add")
    public String addUniversity(@RequestParam String name) {
        University newUniversity = new University();
        newUniversity.setUniversityName(name);

        universityService.saveUniversity(newUniversity);

        return "redirect:/university";
    }

    @PostMapping("/delete")
    @Transactional
    public String deleteUniversity(@RequestParam String name) {
        universityService.deleteUniversityByName(name);

        return "redirect:/university";
    }




}
