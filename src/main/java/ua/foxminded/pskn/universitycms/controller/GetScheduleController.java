package ua.foxminded.pskn.universitycms.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import ua.foxminded.pskn.universitycms.dto.ProfessorDTO;
import ua.foxminded.pskn.universitycms.dto.ScheduleDTO;
import ua.foxminded.pskn.universitycms.dto.StudentDTO;
import ua.foxminded.pskn.universitycms.dto.UserDTO;
import ua.foxminded.pskn.universitycms.service.university.ScheduleService;
import ua.foxminded.pskn.universitycms.service.user.ProfessorService;
import ua.foxminded.pskn.universitycms.service.user.StudentService;
import ua.foxminded.pskn.universitycms.service.user.UserService;

@Controller
@RequiredArgsConstructor
public class GetScheduleController {

    private final ScheduleService scheduleService;

    private final ProfessorService professorService;

    private final UserService userService;

    private final StudentService studentService;

    @GetMapping("/professorscab/schedule")
    public String getProfessorsSchedule(@RequestParam(name = "username") String username, Model model, RedirectAttributes redirectAttributes) {
        Optional<UserDTO> professorByUsername = userService.findProfessorByUsername(username);

        if (professorByUsername.isPresent()) {
            Optional<ProfessorDTO> professor = professorService.getProfessorByUserId(professorByUsername.get().getUserId());
            if (professor.isPresent()) {
                List<ScheduleDTO> professorsSchedule = scheduleService.getScheduleByProfessorId(professor.get().getProfessorId());

                model.addAttribute("professorsSchedule", professorsSchedule);
            }
        }
        else {
            redirectAttributes.addFlashAttribute("professorIsEmpty", "professor is empty!");
        }
        return "professorschedule";
    }

    @GetMapping("/studentscab/schedule")
    public String getStudentSchedule(@RequestParam(name = "username") String username, Model model, RedirectAttributes redirectAttributes) {

        Optional<UserDTO> studentByUsername = userService.findStudentByUsername(username);

        if (studentByUsername.isPresent()) {
            Optional<StudentDTO> student = studentService.getStudentByUserId(studentByUsername.get().getUserId());
            if (student.isPresent()) {
                List<ScheduleDTO> studentSchedule = scheduleService.getScheduleById(student.get().getGroupId());
                model.addAttribute("studentSchedule", studentSchedule);
            }
        }
        else {
            redirectAttributes.addFlashAttribute("studentIsEmpty", "student is empty!");
        }
        return "studentschedule";
    }
}
