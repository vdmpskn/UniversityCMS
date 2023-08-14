package ua.foxminded.pskn.universitycms.controller.university;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.foxminded.pskn.universitycms.dto.UniversityDTO;
import ua.foxminded.pskn.universitycms.model.university.University;
import ua.foxminded.pskn.universitycms.service.university.FacultyService;
import ua.foxminded.pskn.universitycms.service.university.UniversityService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/university")
public class UniversityController {

    private final UniversityService universityService;

    private final FacultyService facultyService;

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
        UniversityDTO universityDTO = new UniversityDTO();
        universityDTO.setUniversityName(name);

        universityService.saveUniversity(universityDTO.toUniversity());

        redirectAttributes.addFlashAttribute("successUniversityMessage", "University added successfully!");
        return "redirect:/university";
    }


    @PostMapping("/delete")
    public String deleteUniversity(@RequestParam("name") String universityName, RedirectAttributes redirectAttributes) {
        University university = universityService.findUniversityByName(universityName);

        if (university != null) {
            if (facultyService.hasFacultiesWithUniversityId(university.getUniversityId())) {
                redirectAttributes.addFlashAttribute("failDeleteUniversity", "Cannot delete university as it has associated faculties.");
            } else {
                universityService.deleteUniversity(university.getUniversityId());
                redirectAttributes.addFlashAttribute("deleteUniversityMessage", "University deleted successfully!");
            }
        } else {
            redirectAttributes.addFlashAttribute("failDeleteUniversity", "University not found");
        }

        return "redirect:/university";
    }

    @PostMapping("/edit")
    public String editUniversity(Long id, String name, RedirectAttributes redirectAttributes){
        if (id != null && name != null) {
            universityService.updateUniversityName(name, id);
            redirectAttributes.addFlashAttribute("editUniversityMessage", "University edited successfully!");
        } else {
            redirectAttributes.addFlashAttribute("failEditUniversity", "Failed to edit university.");
        }
        return "redirect:/university";
    }

}
