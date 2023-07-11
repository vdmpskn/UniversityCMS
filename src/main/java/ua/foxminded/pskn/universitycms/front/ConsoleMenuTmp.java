package ua.foxminded.pskn.universitycms.front;

import org.springframework.stereotype.Component;
import ua.foxminded.pskn.universitycms.model.user.User;
import ua.foxminded.pskn.universitycms.service.UserService;

import java.util.Scanner;

@Component
public class ConsoleMenuTmp {

    private final UserService userService;
    private final Scanner scanner;

    public ConsoleMenuTmp(UserService userService) {
        this.userService = userService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean exit = false;
        while (!exit) {
            System.out.println("1. Вывести всех пользователей");
            System.out.println("2. Найти пользователя по ID");
            System.out.println("3. Добавить нового студента");
            System.out.println("4. Добавить нового профессора");
            System.out.println("5. Удалить пользователя");
            System.out.println("0. Выход");

            System.out.print("Введите номер операции: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> printAllUsers();
                case 2 -> findUserById();
                case 3 -> addStudent();
                case 4 -> addProfessor();
                case 5 -> deleteUser();
                case 0 -> exit = true;
                default -> System.out.println("Неверный номер операции!");
            }

            System.out.println();
        }
    }

    private void printAllUsers() {
        System.out.println("Список пользователей:");
        userService.getAllUsers().forEach(user -> System.out.println(user.getUsername() + " - " + user.getRole()));
    }

    private void findUserById() {
        System.out.print("Введите ID пользователя: ");
        Long id = scanner.nextLong();
        User user = userService.getUserById(id);
        if (user != null) {
            System.out.println("Пользователь найден:");
            System.out.println(user.getUsername() + " - " + user.getRole());
        } else {
            System.out.println("Пользователь не найден!");
        }
    }

    private void addStudent() {
        scanner.nextLine();
        System.out.print("Введите имя студента: ");
        String name = scanner.nextLine();
        System.out.println("Введите пароль студента");
        String pass = scanner.nextLine();
        System.out.print("Введите факультет студента: ");
        int faculty = scanner.nextInt();


        User user = new User();
        user.setUsername(name);
        user.setRole("student");
        user.setPassword(pass);
        user.setFacultyId(faculty);

        userService.saveStudent(user);
        System.out.println("Пользователь успешно добавлен!");
    }

    private void addProfessor(){
        scanner.nextLine();
        System.out.print("Введите имя преподователя: ");
        String name = scanner.nextLine();
        System.out.println("Введите пароль преподователя");
        String pass = scanner.nextLine();
        System.out.print("Введите факультет преподователя: ");
        int faculty = scanner.nextInt();


        User user = new User();
        user.setUsername(name);
        user.setRole("professor");
        user.setPassword(pass);
        user.setFacultyId(faculty);

        userService.saveProfessor(user);
        System.out.println("Преподователь успешно добавлен!");

    }


    private void deleteUser() {
        System.out.print("Введите ID пользователя: ");
        Long id = scanner.nextLong();
        userService.deleteUser(id);
        System.out.println("Пользователь успешно удален!");
    }
}

