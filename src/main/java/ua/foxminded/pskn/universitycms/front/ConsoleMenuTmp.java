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
            System.out.println("1. Display all users");
            System.out.println("2. Find user by ID");
            System.out.println("3. Add a new student");
            System.out.println("4. Add a new professor");
            System.out.println("5. Delete a user");
            System.out.println("0. Exit");

            System.out.print("Enter the operation number: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> printAllUsers();
                case 2 -> findUserById();
                case 3 -> addStudent();
                case 4 -> addProfessor();
                case 5 -> deleteUser();
                case 0 -> exit = true;
                default -> System.out.println("Invalid operation number!");
            }

            System.out.println();
        }
    }

    private void printAllUsers() {
        System.out.println("List of users:");
        userService.getAllUsers().forEach(user -> System.out.println(user.getUsername() + " - " + user.getRole()));
    }

    private void findUserById() {
        System.out.print("Enter the user ID: ");
        Long id = scanner.nextLong();
        User user = userService.getUserById(id);
        if (user != null) {
            System.out.println("User found:");
            System.out.println(user.getUsername() + " - " + user.getRole());
        } else {
            System.out.println("User not found!");
        }
    }

    private void addStudent() {
        scanner.nextLine();
        System.out.print("Enter the student name: ");
        String name = scanner.nextLine();
        System.out.println("Enter the student password: ");
        String pass = scanner.nextLine();
        System.out.print("Enter the student faculty: ");
        int faculty = scanner.nextInt();


        User user = new User();
        user.setUsername(name);
        user.setRole("student");
        user.setPassword(pass);
        user.setFacultyId(faculty);

        userService.saveStudent(user);
        System.out.println("User successfully added!");
    }

    private void addProfessor() {
        scanner.nextLine();
        System.out.print("Enter the professor name: ");
        String name = scanner.nextLine();
        System.out.println("Enter the professor password: ");
        String pass = scanner.nextLine();
        System.out.print("Enter the professor faculty: ");
        int faculty = scanner.nextInt();


        User user = new User();
        user.setUsername(name);
        user.setRole("professor");
        user.setPassword(pass);
        user.setFacultyId(faculty);

        userService.saveProfessor(user);
        System.out.println("Professor successfully added!");
    }


    private void deleteUser() {
        System.out.print("Enter the user ID: ");
        Long id = scanner.nextLong();
        userService.deleteUser(id);
        System.out.println("User successfully deleted!");
    }
}
