package ua.foxminded.pskn.universitycms.controller.university;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.foxminded.pskn.universitycms.model.university.University;
import ua.foxminded.pskn.universitycms.service.university.UniversityService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/university")
public class UniversityController {

    private final UniversityService universityService;

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
    public String addUniversity(String name, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("successUniversityMessage", "University added successfully!");
        universityService.saveUniversityByName(name);
        return "redirect:/adminscab";
    }

    @PostMapping("/delete")
    public String deleteUniversity(String name, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("deleteUniversityMessage", "University deleted successfully!");
        universityService.deleteUniversityByName(name);
        return "redirect:/adminscab";
    }
}
