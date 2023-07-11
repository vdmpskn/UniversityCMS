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
            System.out.println("1. Вывести все университеты");
            System.out.println("2. Вывести все факультеты");
            System.out.println("3. Вывести все группы");
            System.out.println("4. Добавить новый университет");
            System.out.println("5. Добавить новый факультет");
            System.out.println("6. Добавить новую группу");
            System.out.println("7. Удалить университет");
            System.out.println("8. Удалить факультет");
            System.out.println("9. Удалить группу");
            System.out.println("10. Добавить администратора");
            System.out.println("0. Выход");

            System.out.print("Введите номер операции: ");
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
                default -> System.out.println("Неверный номер операции!");
            }

            System.out.println();
        }
    }

    private void printAllUniversities() {
        System.out.println("Список университетов:");
        universityManagementService.getAllUniversities().forEach(university -> System.out.println(university.getUniversityId() + " - " + university.getUniversityName()));
    }

    private void printAllFaculties() {
        System.out.println("Список факультетов:");
        universityManagementService.getAllFaculties().forEach(faculty -> System.out.println(faculty.getFacultyId() + " - " +faculty.getFacultyName()));
    }

    private void printAllStudentGroups() {
        System.out.println("Список групп:");
        universityManagementService.getAllStudentGroups().forEach(studentGroup -> System.out.println(studentGroup.getGroupId() + " - " + studentGroup.getGroupName()));
    }

    private void addUniversity() {
        scanner.nextLine();
        System.out.print("Введите название университета: ");
        String name = scanner.nextLine();

        University university = new University();
        university.setUniversityName(name);

        universityManagementService.saveUniversity(university);
        System.out.println("Университет успешно добавлен!");
    }

    private void addFaculty() {
        scanner.nextLine();
        System.out.print("Введите название факультета: ");
        String name = scanner.nextLine();
        System.out.print("Введите ID университета: ");
        int universityId = scanner.nextInt();

        Faculty faculty = new Faculty();
        faculty.setFacultyName(name);
        faculty.setUniversityId(universityId);

        universityManagementService.saveFaculty(faculty);
        System.out.println("Факультет успешно добавлен!");
    }

    private void addStudentGroup() {
        scanner.nextLine();
        System.out.print("Введите название группы: ");
        String name = scanner.nextLine();
        System.out.print("Введите ID факультета: ");
        int facultyId = scanner.nextInt();

        StudentGroup studentGroup = new StudentGroup();
        studentGroup.setGroupName(name);
        studentGroup.setFacultyId(facultyId);

        universityManagementService.saveStudentGroup(studentGroup);
        System.out.println("Группа успешно добавлена!");
    }

    private void deleteUniversity() {
        System.out.print("Введите ID университета: ");
        Long id = scanner.nextLong();
        universityManagementService.deleteUniversity(id);
        System.out.println("Университет успешно удален!");
    }

    private void deleteFaculty() {
        System.out.print("Введите ID факультета: ");
        Long id = scanner.nextLong();
        universityManagementService.deleteFaculty(id);
        System.out.println("Факультет успешно удален!");
    }

    private void deleteStudentGroup() {
        System.out.print("Введите ID группы: ");
        Long id = scanner.nextLong();
        universityManagementService.deleteStudentGroup(id);
        System.out.println("Группа успешно удалена!");
    }

    private void addAdmin() {
        scanner.nextLine();
        System.out.print("Введите имя админа: ");
        String name = scanner.nextLine();
        System.out.println("Введите пароль админа");
        String pass = scanner.nextLine();
        System.out.print("Введите факультет админа: ");
        int faculty = scanner.nextInt();

        User user = new User();
        user.setUsername(name);
        user.setRole("admin");
        user.setPassword(pass);
        user.setFacultyId(faculty);
        userService.saveAdmin(user);

        System.out.println("Пользователь успешно добавлен!");
    }
}

