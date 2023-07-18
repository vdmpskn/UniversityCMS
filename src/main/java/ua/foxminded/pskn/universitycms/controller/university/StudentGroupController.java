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
import ua.foxminded.pskn.universitycms.model.university.StudentGroup;
import ua.foxminded.pskn.universitycms.service.university.StudentGroupService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/studentgroup")
public class StudentGroupController {

    private final StudentGroupService studentGroupService;

    @GetMapping
    public String studentGroupPage(Model model, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<StudentGroup> studentGroupPage = studentGroupService.getAllStudentGroups(pageable);

        model.addAttribute("student_group", studentGroupPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", studentGroupPage.getTotalPages());
        return "studentgroup";
    }
}
