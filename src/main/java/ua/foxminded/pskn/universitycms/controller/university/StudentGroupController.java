package ua.foxminded.pskn.universitycms.controller.university;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.foxminded.pskn.universitycms.converter.studentgroup.StudentGroupDTOToStudentGroupConverter;
import ua.foxminded.pskn.universitycms.converter.studentgroup.StudentGroupToStudentGroupDTOConverter;
import ua.foxminded.pskn.universitycms.dto.StudentGroupDTO;
import ua.foxminded.pskn.universitycms.dto.UniversityDTO;
import ua.foxminded.pskn.universitycms.model.university.StudentGroup;
import ua.foxminded.pskn.universitycms.model.university.University;
import ua.foxminded.pskn.universitycms.service.university.StudentGroupService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/studentgroup")
public class StudentGroupController {

    private final StudentGroupService studentGroupService;

    private final StudentGroupDTOToStudentGroupConverter toStudentGroupConverter;

    private final StudentGroupToStudentGroupDTOConverter toStudentGroupDTOConverter;

    @GetMapping
    public String studentGroupPage(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<StudentGroup> studentGroupPage = studentGroupService.getAllStudentGroups(pageable);

        model.addAttribute("studentGroup", studentGroupPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", studentGroupPage.getTotalPages());

        model.addAttribute("studentGroupDTO", new StudentGroupDTO());
        return "/university/studentgroup";
    }

    @PostMapping("/add")
    public String addStudentGroup(@ModelAttribute("studentGroupDTO")StudentGroupDTO studentGroupDTO, RedirectAttributes redirectAttributes) {
        studentGroupService.saveStudentGroup(studentGroupDTO);
        redirectAttributes.addFlashAttribute("successStudentGroupMessage", "Student Group added successfully!");

        return "redirect:/studentgroup";
    }

    @PostMapping("/delete")
    public String deleteStudentGroup(@ModelAttribute("studentGroupDTO") StudentGroupDTO studentGroupDTO, RedirectAttributes redirectAttributes) {
            studentGroupService.deleteStudentGroup(studentGroupDTO.getStudentGroupId());
            redirectAttributes.addFlashAttribute("deleteStudentGroupMessage", "Student Group deleted successfully!");
        return "redirect:/studentgroup";
    }


    @PostMapping("/edit")
    public String editStudentGroup(@ModelAttribute("studentGroupDTO") StudentGroupDTO studentGroupDTO, RedirectAttributes redirectAttributes) {
        if (studentGroupDTO != null) {
            studentGroupService.updateStudentGroupName(studentGroupDTO);
            redirectAttributes.addFlashAttribute("editStudentGroupMessage", "Student Group edited successfully!");
        } else {
            redirectAttributes.addFlashAttribute("failStudentGroupMessage", "Failed to edit student group.");
        }
        return "redirect:/studentgroup";
    }
}
