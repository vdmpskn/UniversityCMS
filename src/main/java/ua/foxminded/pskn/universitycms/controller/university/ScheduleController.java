package ua.foxminded.pskn.universitycms.controller.university;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.foxminded.pskn.universitycms.model.university.Schedule;
import ua.foxminded.pskn.universitycms.repository.university.ScheduleRepository;

import java.util.List;

@Controller
@RequestMapping("/schedule.html")
public class ScheduleController {
    private final ScheduleRepository scheduleRepository;

    public ScheduleController(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @GetMapping
    public String schedulePage(Model model) {
        List<Schedule> schedule = scheduleRepository.findAll();
        model.addAttribute("schedule", schedule);
        return "schedule";
    }
}
