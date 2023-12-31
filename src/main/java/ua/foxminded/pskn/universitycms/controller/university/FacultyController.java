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
import ua.foxminded.pskn.universitycms.dto.FacultyDTO;
import ua.foxminded.pskn.universitycms.service.university.FacultyService;
import ua.foxminded.pskn.universitycms.service.university.UniversityService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/faculty")
public class FacultyController {

    private final FacultyService facultyService;

    private final UniversityService universityService;

    @GetMapping
    public String facultyPage(Model model,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "5") int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<FacultyDTO> facultyPage = facultyService.getAllFaculties(pageable);

        model.addAttribute("faculty", facultyPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", facultyPage.getTotalPages());

        model.addAttribute("facultyDTO", new FacultyDTO());
        return "/university/faculty";
    }

    @PostMapping("/add")
    public String addFaculty(@ModelAttribute("facultyDTO") FacultyDTO facultyDTO,
                             RedirectAttributes redirectAttributes) {
        if (universityService.isUniversityExistByUniversityId(facultyDTO.getUniversityId())) {
            facultyService.saveFaculty(facultyDTO);
            redirectAttributes.addFlashAttribute("successFacultyMessage", "Faculty added successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorFacultyMessage", "Faculty dont added!");
        }
        return "redirect:/faculty";
    }

    @PostMapping("/delete")
    public String deleteFaculty(@ModelAttribute("facultyDTO") FacultyDTO facultyDTO,
                                RedirectAttributes redirectAttributes) {
        if (facultyDTO != null) {
            facultyService.deleteFacultyById(facultyDTO.getFacultyId());
            redirectAttributes.addFlashAttribute("deleteFacultyMessage", "Faculty deleted successfully!");
        } else {
            redirectAttributes.addFlashAttribute("failDeleteFaculty", "Faculty cant be deleted!");
        }
        return "redirect:/faculty";
    }

    @PostMapping("/edit")
    public String editFaculty(@ModelAttribute("facultyDTO") FacultyDTO facultyDTO,
                              RedirectAttributes redirectAttributes) {
        if (facultyDTO.getFacultyId() != null && facultyDTO.getFacultyName() != null) {
            facultyService.updateFacultyName(facultyDTO);
            redirectAttributes.addFlashAttribute("editFacultyMessage", "Faculty edited successfully!");
        } else {
            redirectAttributes.addFlashAttribute("failToEditFacultyMessage", "Faculty cant be edited!");
        }
        return "redirect:/faculty";
    }
}
