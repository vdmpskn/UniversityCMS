package ua.foxminded.pskn.universitycms.controller.university;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import ua.foxminded.pskn.universitycms.dto.UniversityDTO;
import ua.foxminded.pskn.universitycms.service.university.FacultyService;
import ua.foxminded.pskn.universitycms.service.university.UniversityService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/university")
public class UniversityController {

    private final UniversityService universityService;

    private final FacultyService facultyService;

    @GetMapping
    public String universityPage(Model model,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "5") int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<UniversityDTO> universityPage = universityService.getAllUniversities(pageable);
        model.addAttribute("university", universityPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", universityPage.getTotalPages());

        model.addAttribute("universityDTO", new UniversityDTO());

        return "university/university";
    }

    @PostMapping("/add")
    public String addUniversity(@ModelAttribute("universityDTO") UniversityDTO universityDTO,
                                RedirectAttributes redirectAttributes) {
        universityService.saveUniversity(universityDTO);
        redirectAttributes.addFlashAttribute("successUniversityMessage", "University added successfully!");

        return "redirect:/university";
    }

    @PostMapping("/delete")
    public String deleteUniversity(@ModelAttribute("universityDTO") UniversityDTO universityDTO,
                                   RedirectAttributes redirectAttributes) {
        if (facultyService.hasFacultiesWithUniversityId(universityDTO.getUniversityId())) {
            String flashText = "Cannot delete university as it has associated faculties.";
            redirectAttributes.addFlashAttribute("failDeleteUniversity", flashText);
        } else {
            universityService.deleteUniversityByName(universityDTO);
            redirectAttributes.addFlashAttribute("deleteUniversityMessage", "University deleted successfully!");
        }
        return "redirect:/university";
    }


    @PostMapping("/edit")
    public String editUniversity(@ModelAttribute("universityDTO") UniversityDTO universityDTO,
                                 RedirectAttributes redirectAttributes) {
        if (universityDTO != null) {
            universityService.updateUniversityName(universityDTO);
            redirectAttributes.addFlashAttribute("editUniversityMessage", "University edited successfully!");
        } else {
            redirectAttributes.addFlashAttribute("failEditUniversity", "Failed to edit university.");
        }
        return "redirect:/university";
    }
}
