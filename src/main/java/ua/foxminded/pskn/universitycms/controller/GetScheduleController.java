package ua.foxminded.pskn.universitycms.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import ua.foxminded.pskn.universitycms.dto.ScheduleDTO;
import ua.foxminded.pskn.universitycms.service.university.ScheduleService;

@Controller
@RequiredArgsConstructor
public class GetScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping("/professorscab/schedule")
    public String getProfessorsSchedule(@RequestParam(name = "userId") Long userId,
                                        Model model,
                                        RedirectAttributes redirectAttributes) {
        List<ScheduleDTO> professorsSchedule = scheduleService.getProfessorSchedule(userId);

        if (professorsSchedule != null) {
            model.addAttribute("professorsSchedule", professorsSchedule);
        } else {
            redirectAttributes.addFlashAttribute("professorIsEmpty", "professor is empty!");
        }
        return "professorschedule";
    }

    @GetMapping("/studentscab/schedule")
    public String getStudentSchedule(@RequestParam(name = "userId") Long userId,
                                     Model model,
                                     RedirectAttributes redirectAttributes) {

        List<ScheduleDTO> studentSchedule = scheduleService.getStudentSchedule(userId);

        if (studentSchedule != null) {
            model.addAttribute("studentSchedule", studentSchedule);
        } else {
            redirectAttributes.addFlashAttribute("studentIsEmpty", "student is empty!");
        }
        return "studentschedule";
    }
}
