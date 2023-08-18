package ua.foxminded.pskn.universitycms.controller.university;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.foxminded.pskn.universitycms.customexception.FacultyEditException;
import ua.foxminded.pskn.universitycms.customexception.FacultyNotFoundException;
import ua.foxminded.pskn.universitycms.customexception.UniversityNotFoundException;
import ua.foxminded.pskn.universitycms.dto.FacultyDTO;
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

        model.addAttribute("facultyDTO", new FacultyDTO());
        return "/university/faculty";
    }

    @PostMapping("/add")
    public String addFaculty(@ModelAttribute("facultyDTO") FacultyDTO facultyDTO, RedirectAttributes redirectAttributes) {
        try {
            if (universityService.isUniversityExistByUniversityId(facultyDTO.getUniversityId())) {
                facultyService.saveFaculty(facultyDTO.toFaculty());
                redirectAttributes.addFlashAttribute("successFacultyMessage", "Faculty added successfully!");
            } else {
                throw new UniversityNotFoundException("University with the provided ID does not exist.");
            }
        } catch (UniversityNotFoundException ex) {
            redirectAttributes.addFlashAttribute("errorFacultyMessage", ex.getMessage());
        }
        return "redirect:/faculty";
    }

    @PostMapping("/delete")
    public String deleteFaculty(@ModelAttribute("facultyDTO") FacultyDTO facultyDTO, RedirectAttributes redirectAttributes) {
        try {
            boolean deletionSuccessful = facultyService.deleteFacultyById(facultyDTO.getFacultyId());
            if (deletionSuccessful) {
                redirectAttributes.addFlashAttribute("deleteFacultyMessage", "Faculty deleted successfully!");
            } else {
                throw new FacultyNotFoundException("Faculty not found.");
            }
        } catch (DataIntegrityViolationException ex) {
            redirectAttributes.addFlashAttribute("failDeleteFaculty", ex.getMessage());
        }
        return "redirect:/faculty";
    }


    @PostMapping("/edit")
    public String editFaculty(@ModelAttribute("facultyDTO") FacultyDTO facultyDTO, RedirectAttributes redirectAttributes) {
        try {
            if (facultyDTO.getFacultyId() != null && facultyDTO.getFacultyName() != null) {
                facultyService.updateFacultyName(facultyDTO);
                redirectAttributes.addFlashAttribute("editFacultyMessage", "Faculty edited successfully!");
            } else {
                throw new FacultyEditException("Failed to edit faculty.");
            }
        } catch (FacultyEditException ex) {
            redirectAttributes.addFlashAttribute("failEditFaculty", ex.getMessage());
        }
        return "redirect:/faculty";
    }


    @ExceptionHandler(NullPointerException.class)
    public ModelAndView handleNullPointerException(NullPointerException ex) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", "An error occurred: " + ex.getMessage());
        return modelAndView;
    }

}
