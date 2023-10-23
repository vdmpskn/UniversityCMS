package ua.foxminded.pskn.universitycms.service.university;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ua.foxminded.pskn.universitycms.converter.schedule.ScheduleConverter;
import ua.foxminded.pskn.universitycms.customexception.ProfessorNotFoundException;
import ua.foxminded.pskn.universitycms.customexception.ScheduleCreateException;
import ua.foxminded.pskn.universitycms.customexception.ScheduleDeleteException;
import ua.foxminded.pskn.universitycms.customexception.ScheduleUpdateException;
import ua.foxminded.pskn.universitycms.customexception.StudentNotFoundException;
import ua.foxminded.pskn.universitycms.dto.ScheduleDTO;
import ua.foxminded.pskn.universitycms.model.university.Schedule;
import ua.foxminded.pskn.universitycms.repository.university.ScheduleRepository;
import ua.foxminded.pskn.universitycms.service.user.ProfessorService;
import ua.foxminded.pskn.universitycms.service.user.StudentService;
import ua.foxminded.pskn.universitycms.service.user.UserService;

@Slf4j
@RequiredArgsConstructor
@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    private final ScheduleConverter scheduleConverter;

    private final UserService userService;

    private final ProfessorService professorService;

    private final StudentService studentService;

    public List<ScheduleDTO> getScheduleByGroupId(Long groupId) {
        log.debug("Retrieving schedule for group ID: {}", groupId);

        return scheduleRepository.findScheduleByGroupId(groupId)
            .stream()
            .map(scheduleConverter::convertToDTO)
            .toList();
    }

    public List<ScheduleDTO> getProfessorSchedule(Long userId) {
        return userService.findProfessorById(userId)
            .map(userDTO -> professorService.getProfessorByUserId(userDTO.getUserId()))
            .map(professorDTO -> getScheduleByProfessorId(professorDTO.getProfessorId()))
            .orElseThrow(() -> new ProfessorNotFoundException("Professor not found."));
    }

    public List<ScheduleDTO> getStudentSchedule(Long userId) {
        return userService.findStudentById(userId)
            .map(userDTO -> studentService.getStudentByUserId(userDTO.getUserId()))
            .map(studentDTO -> getScheduleByGroupId(studentDTO.getGroupId()))
            .orElseThrow(() -> new StudentNotFoundException("Student not found. "));
    }

    public List<ScheduleDTO> getScheduleByProfessorId(Long professorId) {
        if (professorId <= 0) {
            throw new IllegalArgumentException("Wrong value of 'userId'");
        }
        log.debug("Retrieving schedule for professor ID: {}", professorId);
        return scheduleRepository.findScheduleByProfessorId(professorId)
            .stream()
            .map(scheduleConverter::convertToDTO)
            .toList();
    }

    public List<Schedule> getAllSchedule() {
        log.debug("Retrieving all schedules");
        return scheduleRepository.findAll();
    }

    public void saveSchedule(ScheduleDTO scheduleDTO) {
        if (scheduleDTO != null) {
            scheduleRepository.save(scheduleConverter.convertToEntity(scheduleDTO));
            log.info("Schedule with id {} saved successful", scheduleDTO.getScheduleId());
        }
        else
            throw new ScheduleCreateException("Schedule cant be created");
    }

    @Transactional
    public void deleteScheduleById(Long scheduleId) {
        if (scheduleId != null) {
            scheduleRepository.deleteById(scheduleId);
            log.info("Schedule with id {} deleted successful", scheduleId);
        }
        else {
            throw new ScheduleDeleteException("Schedule delete exception");
        }
    }

    @Transactional
    public void updateSchedule(ScheduleDTO scheduleDTO) {
        if (scheduleDTO != null) {
            ScheduleDTO newSchedule = ScheduleDTO.builder()
                .scheduleId(scheduleDTO.getScheduleId())
                .courseId(scheduleDTO.getCourseId())
                .groupId(scheduleDTO.getGroupId())
                .professorId(scheduleDTO.getProfessorId())
                .startTime(scheduleDTO.getStartTime())
                .endTime(scheduleDTO.getEndTime())
                .date(scheduleDTO.getDate())
                .build();

            scheduleRepository.save(scheduleConverter.convertToEntity(newSchedule));
            log.info("Schedule with ID {} updated successful", newSchedule.getScheduleId());
        }
        else {
            throw new ScheduleUpdateException("Schedule cant be updated");
        }
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
