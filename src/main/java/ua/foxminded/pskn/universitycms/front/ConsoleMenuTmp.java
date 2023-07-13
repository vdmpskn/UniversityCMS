package ua.foxminded.pskn.universitycms.front;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ua.foxminded.pskn.universitycms.model.university.Schedule;
import ua.foxminded.pskn.universitycms.model.user.Student;
import ua.foxminded.pskn.universitycms.model.user.User;
import ua.foxminded.pskn.universitycms.service.university.ScheduleService;
import ua.foxminded.pskn.universitycms.service.user.StudentService;
import ua.foxminded.pskn.universitycms.service.user.UserService;

import java.util.List;
import java.util.Scanner;

@RequiredArgsConstructor
@Slf4j
@Component
public class ConsoleMenuTmp {

    private final UserService userService;
    private final StudentService studentService;
    private final ScheduleService scheduleService;
    Scanner scanner = new Scanner(System.in);

    public void startStudent(User user, Long groupId, Student student) {
        boolean exit = false;
        while (!exit) {
            log.info("1. Get my schedule");
            log.info("2. Change my username");
            log.info("3. Change my faculty");
            log.info("4. Change my group");
            log.info("0. Exit");

            log.info("Enter the operation number: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> log.info(getStudentSchedule(groupId).toString());
                case 2 -> changeUsername(user);
                case 3 -> changeFaculty(user);
                case 4 -> changeGroup(student);
                case 0 -> exit = true;
                default -> log.info("Invalid operation number!");
            }
        }
    }

    public void startProfessor(User user, int professorId) {
        boolean exit = false;
        while (!exit) {
            log.info("1. Get my schedule");
            log.info("2. Change my username");
            log.info("3. Change my faculty");
            log.info("0. Exit");

            log.info("Enter the operation number: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> log.info(getProfessorSchedule(professorId).toString());
                case 2 -> changeUsername(user);
                case 3 -> changeFaculty(user);
                case 0 -> exit = true;
                default -> log.info("Invalid operation number!");
            }
        }
    }

    private List<Schedule> getStudentSchedule(Long groupId) {
        return scheduleService.getScheduleById(groupId);}

    private List<Schedule> getProfessorSchedule(int professorId) {
        return scheduleService.getScheduleByProfessorId(professorId);}

    private void changeUsername(User user) {
        userService.changeMyName(user);
    }

    private void changeFaculty(User user) {
        userService.changeMyFaculty(user);
    }

    private void changeGroup(Student student) {
        studentService.changeMyGroup(student);
    }
}
