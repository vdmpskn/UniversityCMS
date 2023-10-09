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
import ua.foxminded.pskn.universitycms.dto.StudentGroupDTO;
import ua.foxminded.pskn.universitycms.service.university.StudentGroupService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/studentgroup")
public class StudentGroupController {

    private final StudentGroupService studentGroupService;

    @GetMapping
    public String studentGroupPage(Model model,
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "5") int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<StudentGroupDTO> studentGroupPage = studentGroupService.getAllStudentGroups(pageable);

        model.addAttribute("studentGroup", studentGroupPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", studentGroupPage.getTotalPages());

        model.addAttribute("studentGroupDTO", new StudentGroupDTO());
        return "/university/studentgroup";
    }

    @PostMapping("/add")
    public String addStudentGroup(@ModelAttribute("studentGroupDTO") StudentGroupDTO studentGroupDTO,
                                  RedirectAttributes redirectAttributes) {
        studentGroupService.saveStudentGroup(studentGroupDTO);
        redirectAttributes.addFlashAttribute("successStudentGroupMessage", "Student Group added successfully!");

        return "redirect:/studentgroup";
    }

    @PostMapping("/delete")
    public String deleteStudentGroup(@ModelAttribute("studentGroupDTO") StudentGroupDTO studentGroupDTO,
                                     RedirectAttributes redirectAttributes) {
        studentGroupService.deleteStudentGroup(studentGroupDTO.getGroupId());
        redirectAttributes.addFlashAttribute("deleteStudentGroupMessage", "Student Group deleted successfully!");
        return "redirect:/studentgroup";
    }


    @PostMapping("/edit")
    public String editStudentGroup(@ModelAttribute("studentGroupDTO") StudentGroupDTO studentGroupDTO,
                                   RedirectAttributes redirectAttributes) {
        if (studentGroupDTO != null) {
            studentGroupService.updateStudentGroupName(studentGroupDTO);
            redirectAttributes.addFlashAttribute("editStudentGroupMessage", "Student Group edited successfully!");
        } else {
            redirectAttributes.addFlashAttribute("failStudentGroupMessage", "Failed to edit student group.");
        }
        return "redirect:/studentgroup";
    }
}
