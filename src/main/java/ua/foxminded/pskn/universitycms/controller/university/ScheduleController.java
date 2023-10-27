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
import ua.foxminded.pskn.universitycms.customexception.ScheduleCreateException;
import ua.foxminded.pskn.universitycms.customexception.ScheduleDeleteException;
import ua.foxminded.pskn.universitycms.customexception.ScheduleUpdateException;
import ua.foxminded.pskn.universitycms.dto.ScheduleDTO;
import ua.foxminded.pskn.universitycms.service.university.ScheduleService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping
    public String schedulePage(Model model,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "5") int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<ScheduleDTO> schedulePage = scheduleService.getAllSchedule(pageable);

        model.addAttribute("schedule", schedulePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", schedulePage.getTotalPages());

        model.addAttribute("scheduleDTO", new ScheduleDTO());
        return "/university/schedule";
    }

    @PostMapping("/add")
    public String addSchedule(@ModelAttribute("scheduleDTO") ScheduleDTO scheduleDTO,
                              RedirectAttributes redirectAttributes) {
        try {
            scheduleService.saveSchedule(scheduleDTO);
            redirectAttributes.addFlashAttribute("successAddSchedule", "Schedule added success");
        }
        catch (ScheduleCreateException e){
            redirectAttributes.addFlashAttribute("failAddSchedule", "Fail added schedule");
        }
        return "redirect:/schedule";
    }

    @PostMapping("/edit")
    public String editSchedule(@ModelAttribute("scheduleDTO") ScheduleDTO scheduleDTO,
                               RedirectAttributes redirectAttributes) {
        try {
            scheduleService.updateSchedule(scheduleDTO);
            redirectAttributes.addFlashAttribute("successEditSchedule", "Schedule edited success");
        }
        catch (ScheduleUpdateException e) {
            redirectAttributes.addFlashAttribute("failEditSchedule", "Fail edited schedule");
        }
        return "redirect:/schedule";
    }

    @PostMapping("/delete")
    public String deleteSchedule(@ModelAttribute("scheduleDTO") ScheduleDTO scheduleDTO,
                                 RedirectAttributes redirectAttributes) {
        try {
            scheduleService.deleteScheduleById(scheduleDTO.getScheduleId());
            redirectAttributes.addFlashAttribute("successDeleteSchedule", "Schedule deleted success");
        }
        catch (ScheduleDeleteException e) {
            redirectAttributes.addFlashAttribute("failDeleteSchedule", "Fail deleted schedule");
        }
        return "redirect:/schedule";
    }
}
