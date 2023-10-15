package ua.foxminded.pskn.universitycms.service.university;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ua.foxminded.pskn.universitycms.converter.schedule.ScheduleConverter;
import ua.foxminded.pskn.universitycms.customexception.ProfessorNotFoundException;
import ua.foxminded.pskn.universitycms.customexception.StudentNotFoundException;
import ua.foxminded.pskn.universitycms.dto.ProfessorDTO;
import ua.foxminded.pskn.universitycms.dto.ScheduleDTO;
import ua.foxminded.pskn.universitycms.dto.StudentDTO;
import ua.foxminded.pskn.universitycms.dto.UserDTO;
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

    public List<ScheduleDTO> getScheduleByGroupId(int groupId) {
        log.debug("Retrieving schedule for group ID: {}", groupId);

        return scheduleRepository.findScheduleByGroupId(groupId)
            .stream()
            .map(scheduleConverter::convertToDTO)
            .toList();
    }

    public List<ScheduleDTO> getProfessorSchedule(String username) {
        Optional<UserDTO> professorByUsername = userService.findProfessorByUsername(username);

        if (professorByUsername.isPresent()) {
            Optional<ProfessorDTO> professor = professorService.getProfessorByUserId(professorByUsername.get().getUserId());
            if (professor.isPresent()) {
                return getScheduleByProfessorId(professor.get().getProfessorId());
            }
        } throw new ProfessorNotFoundException("Professor not found.");
    }

    public List<ScheduleDTO> getStudentSchedule(String username) {
        Optional<UserDTO> studentByUsername = userService.findStudentByUsername(username);

        if (studentByUsername.isPresent()) {
            Optional<StudentDTO> student = studentService.getStudentByUserId(studentByUsername.get().getUserId());
            if (student.isPresent()) {
                return getScheduleByGroupId(student.get().getGroupId());
            }
        } throw new StudentNotFoundException("Student not found. ");
    }

    public List<ScheduleDTO> getScheduleByProfessorId(int professorId) {
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
