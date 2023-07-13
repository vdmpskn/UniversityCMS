package ua.foxminded.pskn.universitycms.front;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ua.foxminded.pskn.universitycms.model.user.Professor;
import ua.foxminded.pskn.universitycms.model.user.Student;
import ua.foxminded.pskn.universitycms.model.user.User;
import ua.foxminded.pskn.universitycms.service.university.ScheduleService;
import ua.foxminded.pskn.universitycms.service.user.ProfessorService;
import ua.foxminded.pskn.universitycms.service.user.UserService;
import ua.foxminded.pskn.universitycms.service.user.StudentService;

import java.util.Scanner;

@RequiredArgsConstructor
@Slf4j
@Component
public class AuthDemo {

    private final UserService userService;
    private final StudentService studentService;
    private final ProfessorService professorService;
    private final AdminConsoleMenuTmp adminConsoleMenu;
    private final ScheduleService scheduleService;
    private final ConsoleMenuTmp consoleMenu;
    Scanner scanner = new Scanner(System.in);

    public void start() {
        log.info("Enter username: ");
        String username = scanner.nextLine();

        User user = userService.getUserByUsername(username);
        if (user != null) {
            log.info("Welcome, " + user.getUsername() + "!");

            if ("admin".equals(user.getRole())) {
                adminConsoleMenu.start();

            } else if (("student".equals(user.getRole()))) {
                Long userId = user.getUserId();
                Student student = studentService.getStudentByUserId(userId);
                Long groupId = (long) student.getGroupId();
                consoleMenu.startStudent(user, groupId, student);

            } else if (("professor".equals(user.getRole()))) {
                Long userId = user.getUserId();
                Professor professor = professorService.getProfessorByUserId(userId);
                int professorId = professor.getProfessorId();
                consoleMenu.startProfessor(user, professorId);
            }
        } else {
            log.info("User not found!");
        }
    }

}
