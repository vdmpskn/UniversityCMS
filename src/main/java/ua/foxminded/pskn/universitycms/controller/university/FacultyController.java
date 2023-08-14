package ua.foxminded.pskn.universitycms.controller.university;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.foxminded.pskn.universitycms.model.university.Faculty;
import ua.foxminded.pskn.universitycms.service.university.FacultyService;
import ua.foxminded.pskn.universitycms.service.university.UniversityService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/faculty")
public class FacultyController {

    private final FacultyService facultyService;

    private final UniversityService universityService;

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
    public String addFaculty(String name, int universityId, RedirectAttributes redirectAttributes) {
        if (universityService.isUniversityExistByUniversityId(universityId)) {
            facultyService.saveFacultyByName(name, universityId);
            redirectAttributes.addFlashAttribute("successFacultyMessage", "Faculty added successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorFacultyMessage", "University with the provided ID does not exist.");
        }
        return "redirect:/faculty";
    }


    @PostMapping("/delete")
    public String deleteFaculty(String name, int universityId, RedirectAttributes redirectAttributes) {
        boolean deletionSuccessful = facultyService.deleteFacultyByName(name, universityId);
        if (deletionSuccessful) {
            redirectAttributes.addFlashAttribute("deleteFacultyMessage", "Faculty deleted successfully!");
        } else {
            redirectAttributes.addFlashAttribute("failDeleteFaculty", "Faculty not found");
        }

        return "redirect:/faculty";
    }

    @PostMapping("/edit")
    public String editFaculty(Long id, String name, RedirectAttributes redirectAttributes){
        if (id != null && name != null) {
            facultyService.updateFacultyName(name, id);
            redirectAttributes.addFlashAttribute("editFacultyMessage", "Faculty edited successfully!");
        } else {
            redirectAttributes.addFlashAttribute("failEditFaculty", "Failed to edit faculty.");
        }
        return "redirect:/faculty";
    }

}
