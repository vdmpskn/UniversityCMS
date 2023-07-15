package ua.foxminded.pskn.universitycms.front;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ua.foxminded.pskn.universitycms.model.user.User;
import ua.foxminded.pskn.universitycms.service.university.FacultyService;
import ua.foxminded.pskn.universitycms.service.university.StudentGroupService;
import ua.foxminded.pskn.universitycms.service.university.UniversityService;
import ua.foxminded.pskn.universitycms.model.university.*;
import ua.foxminded.pskn.universitycms.service.user.ProfessorService;
import ua.foxminded.pskn.universitycms.service.user.UserService;

import java.util.Scanner;
@RequiredArgsConstructor
@Slf4j
@Component
public class AdminConsoleMenuTmp {

    private final UniversityService universityService;
    private final FacultyService facultyService;
    private final StudentGroupService studentGroupService;
    private final UserService userService;
    private final ProfessorService professorService;

    Scanner scanner = new Scanner(System.in);

    public void start() {
        boolean exit = false;
        while (!exit) {
            log.info("----------MANAGE UNIVERSITY-------");
            log.info("1. Display all universities");
            log.info("2. Display all faculties");
            log.info("3. Display all student groups");
            log.info("4. Add a new university");
            log.info("5. Add a new faculty");
            log.info("6. Add a new student group");
            log.info("7. Delete a university");
            log.info("8. Delete a faculty");
            log.info("9. Delete a student group");
            log.info("--------------MANAGE USERS------------");
            log.info("10. Add an administrator");
            log.info("11. Display all users");
            log.info("12. Find user by ID");
            log.info("13. Add a new student");
            log.info("14. Add a new professor");
            log.info("15. Delete a user");
            log.info("0. Exit");

            log.info("Enter the operation number: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> printAllUniversities();
                case 2 -> printAllFaculties();
                case 3 -> printAllStudentGroups();
                case 4 -> addUniversity();
                case 5 -> addFaculty();
                case 6 -> addStudentGroup();
                case 7 -> deleteUniversity();
                case 8 -> deleteFaculty();
                case 9 -> deleteStudentGroup();
                case 10 -> addAdmin();
                case 11 -> printAllUsers();
                case 12 -> findUserById();
                case 13 -> addStudent();
                case 14 -> addProfessor();
                case 15 -> deleteUser();
                case 0 -> exit = true;
                default -> log.info("Wrong operation number!");
            }
        }
    }

    private void printAllUniversities() {
        log.info("List of universities:");
        universityService.getAllUniversities().forEach(university -> log.info(university.getUniversityId() + " - " + university.getUniversityName()));
    }

    private void printAllFaculties() {
        log.info("List of faculties:");
        facultyService.getAllFaculties().forEach(faculty -> log.info(faculty.getFacultyId() + " - " + faculty.getFacultyName()));
    }

    private void printAllStudentGroups() {
        log.info("List of student groups:");
        studentGroupService.getAllStudentGroups().forEach(studentGroup -> log.info(studentGroup.getGroupId() + " - " + studentGroup.getGroupName()));
    }

    private void addUniversity() {
        scanner.nextLine();
        log.info("Enter the name of the university: ");
        String name = scanner.nextLine();

        University university = University.builder()
                .universityName(name)
                .build();

        universityService.saveUniversity(university);
        log.info("University successfully added!");
    }

    private void addFaculty() {
        scanner.nextLine();
        log.info("Enter the name of the faculty: ");
        String name = scanner.nextLine();
        log.info("Enter the university ID: ");
        int universityId = scanner.nextInt();

        Faculty faculty = Faculty.builder()
                .facultyName(name)
                .universityId(universityId)
                .build();

        facultyService.saveFaculty(faculty);
        log.info("Faculty successfully added!");
    }


    private void addStudentGroup() {
        scanner.nextLine();
        log.info("Enter the name of the student group: ");
        String name = scanner.nextLine();
        log.info("Enter the faculty ID: ");
        int facultyId = scanner.nextInt();

        StudentGroup studentGroup = StudentGroup.builder()
                .groupName(name)
                .facultyId(facultyId)
                .build();

        studentGroupService.saveStudentGroup(studentGroup);
        log.info("Student group successfully added!");
    }

    private void deleteUniversity() {
        log.info("Enter the university ID: ");
        Long id = scanner.nextLong();
        universityService.deleteUniversity(id);
        log.info("University successfully deleted!");
    }

    private void deleteFaculty() {
        log.info("Enter the faculty ID: ");
        Long id = scanner.nextLong();
        facultyService.deleteFaculty(id);
        log.info("Faculty successfully deleted!");
    }

    private void deleteStudentGroup() {
        log.info("Enter the student group ID: ");
        Long id = scanner.nextLong();
        studentGroupService.deleteStudentGroup(id);
        log.info("Student group successfully deleted!");
    }

    private void addAdmin() {
        scanner.nextLine();
        log.info("Enter the admin name: ");
        String name = scanner.nextLine();
        log.info("Enter the admin password: ");
        String pass = scanner.nextLine();
        log.info("Enter the admin faculty ID: ");
        int faculty = scanner.nextInt();

        User user = User.builder()
                .username(name)
                .role("admin")
                .password(pass)
                .facultyId(faculty)
                .build();
        userService.saveAdmin(user);

        log.info("User successfully added!");
    }

    private void printAllUsers() {
        log.info("List of users:");
        userService.getAllUsers().forEach(user -> log.info(user.getUsername() + " - " + user.getRole()));
    }

    private void findUserById() {
        log.info("Enter the user ID: ");
        Long id = scanner.nextLong();
        User user = userService.getUserById(id);
        if (user != null) {
            log.info("User found:");
            log.info(user.getUsername() + " - " + user.getRole());
        } else {
            log.info("User not found!");
        }
    }

    private void addStudent() {
        scanner.nextLine();
        log.info("Enter the student name: ");
        String name = scanner.nextLine();
        log.info("Enter the student password: ");
        String pass = scanner.nextLine();
        log.info("Enter the student faculty: ");
        int faculty = scanner.nextInt();


        User user = User.builder()
                .username(name)
                .role("student")
                .password(pass)
                .facultyId(faculty)
                .build();
        userService.saveStudent(user);
        log.info("User successfully added!");
    }

    private void addProfessor() {
        scanner.nextLine();
        log.info("Enter the professor name: ");
        String name = scanner.nextLine();
        log.info("Enter the professor password: ");
        String pass = scanner.nextLine();
        log.info("Enter the professor faculty: ");
        int faculty = scanner.nextInt();

        User user = User.builder()
                .username(name)
                .role("professor")
                .password(pass)
                .facultyId(faculty)
                .build();
        userService.saveProfessor(user);
        log.info("Professor successfully added!");
    }


    private void deleteUser() {
        log.info("Enter the user ID: ");
        Long id = scanner.nextLong();
        userService.deleteUser(id);
        log.info("User successfully deleted!");
    }

}
