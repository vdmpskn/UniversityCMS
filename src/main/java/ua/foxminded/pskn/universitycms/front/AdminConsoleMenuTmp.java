package ua.foxminded.pskn.universitycms.front;

import org.springframework.stereotype.Component;
import ua.foxminded.pskn.universitycms.model.user.User;
import ua.foxminded.pskn.universitycms.service.UniversityManagementService;
import ua.foxminded.pskn.universitycms.model.university.*;
import ua.foxminded.pskn.universitycms.service.UserService;

import java.util.Scanner;

@Component
public class AdminConsoleMenuTmp {

    private final UniversityManagementService universityManagementService;
    private final UserService userService;
    private final Scanner scanner;

    public AdminConsoleMenuTmp(UniversityManagementService universityManagementService, UserService userService) {
        this.universityManagementService = universityManagementService;
        this.userService = userService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean exit = false;
        while (!exit) {
            System.out.println("1. Display all universities");
            System.out.println("2. Display all faculties");
            System.out.println("3. Display all student groups");
            System.out.println("4. Add a new university");
            System.out.println("5. Add a new faculty");
            System.out.println("6. Add a new student group");
            System.out.println("7. Delete a university");
            System.out.println("8. Delete a faculty");
            System.out.println("9. Delete a student group");
            System.out.println("10. Add an administrator");
            System.out.println("0. Exit");

            System.out.print("Enter the operation number: ");
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
                case 0 -> exit = true;
                default -> System.out.println("Wrong operation number!");
            }

            System.out.println();
        }
    }

    private void printAllUniversities() {
        System.out.println("List of universities:");
        universityManagementService.getAllUniversities().forEach(university -> System.out.println(university.getUniversityId() + " - " + university.getUniversityName()));
    }

    private void printAllFaculties() {
        System.out.println("List of faculties:");
        universityManagementService.getAllFaculties().forEach(faculty -> System.out.println(faculty.getFacultyId() + " - " + faculty.getFacultyName()));
    }

    private void printAllStudentGroups() {
        System.out.println("List of student groups:");
        universityManagementService.getAllStudentGroups().forEach(studentGroup -> System.out.println(studentGroup.getGroupId() + " - " + studentGroup.getGroupName()));
    }

    private void addUniversity() {
        scanner.nextLine();
        System.out.print("Enter the name of the university: ");
        String name = scanner.nextLine();

        University university = new University();
        university.setUniversityName(name);

        universityManagementService.saveUniversity(university);
        System.out.println("University successfully added!");
    }

    private void addFaculty() {
        scanner.nextLine();
        System.out.print("Enter the name of the faculty: ");
        String name = scanner.nextLine();
        System.out.print("Enter the university ID: ");
        int universityId = scanner.nextInt();

        Faculty faculty = new Faculty();
        faculty.setFacultyName(name);
        faculty.setUniversityId(universityId);

        universityManagementService.saveFaculty(faculty);
        System.out.println("Faculty successfully added!");
    }

    private void addStudentGroup() {
        scanner.nextLine();
        System.out.print("Enter the name of the student group: ");
        String name = scanner.nextLine();
        System.out.print("Enter the faculty ID: ");
        int facultyId = scanner.nextInt();

        StudentGroup studentGroup = new StudentGroup();
        studentGroup.setGroupName(name);
        studentGroup.setFacultyId(facultyId);

        universityManagementService.saveStudentGroup(studentGroup);
        System.out.println("Student group successfully added!");
    }

    private void deleteUniversity() {
        System.out.print("Enter the university ID: ");
        Long id = scanner.nextLong();
        universityManagementService.deleteUniversity(id);
        System.out.println("University successfully deleted!");
    }

    private void deleteFaculty() {
        System.out.print("Enter the faculty ID: ");
        Long id = scanner.nextLong();
        universityManagementService.deleteFaculty(id);
        System.out.println("Faculty successfully deleted!");
    }

    private void deleteStudentGroup() {
        System.out.print("Enter the student group ID: ");
        Long id = scanner.nextLong();
        universityManagementService.deleteStudentGroup(id);
        System.out.println("Student group successfully deleted!");
    }

    private void addAdmin() {
        scanner.nextLine();
        System.out.print("Enter the admin name: ");
        String name = scanner.nextLine();
        System.out.print("Enter the admin password: ");
        String pass = scanner.nextLine();
        System.out.print("Enter the admin faculty ID: ");
        int faculty = scanner.nextInt();

        User user = new User();
        user.setUsername(name);
        user.setRole("admin");
        user.setPassword(pass);
        user.setFacultyId(faculty);
        userService.saveAdmin(user);

        System.out.println("User successfully added!");
    }
}
