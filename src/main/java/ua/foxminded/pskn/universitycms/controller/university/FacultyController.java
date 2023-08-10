package ua.foxminded.pskn.universitycms.controller.university;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.foxminded.pskn.universitycms.model.university.Faculty;
import ua.foxminded.pskn.universitycms.model.university.University;
import ua.foxminded.pskn.universitycms.service.university.FacultyService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/faculty")
public class FacultyController {

    private final FacultyService facultyService;

    @GetMapping
    public String facultyPage(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Faculty> facultyPage = facultyService.getAllFaculties(pageable);

        model.addAttribute("faculty", facultyPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", facultyPage.getTotalPages());
        return "/university/faculty";
    }

    @PostMapping("/add")
    public String addFaculty(@RequestParam String name, int universityId) {
        Faculty newFaculty = new Faculty();
        newFaculty.setFacultyName(name);
        newFaculty.setUniversityId(universityId);

        facultyService.saveFaculty(newFaculty);

        return "redirect:/faculty";
    }

    @PostMapping("/delete")
    @Transactional
    public String deleteUniversity(@RequestParam String name) {
        facultyService.deleteFacultyByName(name);
        return "redirect:/faculty";
    }

}
