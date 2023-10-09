package ua.foxminded.pskn.universitycms.service.university;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ua.foxminded.pskn.universitycms.converter.schedule.ScheduleConverter;
import ua.foxminded.pskn.universitycms.dto.ScheduleDTO;
import ua.foxminded.pskn.universitycms.model.university.Schedule;
import ua.foxminded.pskn.universitycms.repository.university.ScheduleRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    private final ScheduleConverter scheduleConverter;

    public List<Schedule> getScheduleById(Long groupId) {
        log.debug("Retrieving schedule for group ID: {}", groupId);
        return scheduleRepository.findScheduleByGroupId(groupId);
    }

    public List<Schedule> getScheduleByProfessorId(int professorId) {
        log.debug("Retrieving schedule for professor ID: {}", professorId);
        return scheduleRepository.findScheduleByProfessorId(professorId);
    }

    public List<Schedule> getAllSchedule() {
        log.debug("Retrieving all schedules");
        return scheduleRepository.findAll();
    }

    public Page<ScheduleDTO> getAllSchedule(Pageable pageable) {
        log.debug(
            "Retrieving all schedules with page number: {} and page size: {}",
            pageable.getPageNumber(),
            pageable.getPageSize()
        );

        Page<Schedule> schedulePage = scheduleRepository.findAll(pageable);
        return schedulePage.map(scheduleConverter::convertToDTO);
    }
}
