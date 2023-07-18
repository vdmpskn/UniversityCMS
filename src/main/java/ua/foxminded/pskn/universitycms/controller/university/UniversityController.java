package ua.foxminded.pskn.universitycms.controller.university;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.foxminded.pskn.universitycms.model.university.University;
import ua.foxminded.pskn.universitycms.service.university.UniversityService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/university")
public class UniversityController {
    private final UniversityService universityService;

    @GetMapping
    public String universityPage(Model model, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<University> universityPage = universityService.getAllUniversities(pageable);
        model.addAttribute("university", universityPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", universityPage.getTotalPages());
        return "university";
    }
}
