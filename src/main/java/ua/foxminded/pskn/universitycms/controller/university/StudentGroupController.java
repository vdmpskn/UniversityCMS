package ua.foxminded.pskn.universitycms.controller.university;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.foxminded.pskn.universitycms.model.university.StudentGroup;
import ua.foxminded.pskn.universitycms.repository.university.StudentGroupRepository;

import java.util.List;

@Controller
@RequestMapping("/studentgroup.html")
public class StudentGroupController {
    private final StudentGroupRepository studentGroupRepository;


    public StudentGroupController(StudentGroupRepository studentGroupRepository) {
        this.studentGroupRepository = studentGroupRepository;
    }

    @GetMapping
    public String studentGroupPage(Model model) {
        List<StudentGroup> studentGroups = studentGroupRepository.findAll();
        model.addAttribute("student_group", studentGroups);
        return "studentgroup";
    }
}
