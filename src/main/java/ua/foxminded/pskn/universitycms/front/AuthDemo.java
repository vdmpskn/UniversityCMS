package ua.foxminded.pskn.universitycms.front;

import org.springframework.stereotype.Component;
import ua.foxminded.pskn.universitycms.model.user.User;
import ua.foxminded.pskn.universitycms.service.UserService;

import java.util.Scanner;

@Component
public class AuthDemo {

    private final UserService userService;
    private final AdminConsoleMenuTmp adminConsoleMenu;
    private final ConsoleMenuTmp consoleMenu;
    private final Scanner scanner;

    public AuthDemo(UserService userService, AdminConsoleMenuTmp adminConsoleMenu, ConsoleMenuTmp consoleMenu) {
        this.userService = userService;
        this.adminConsoleMenu = adminConsoleMenu;
        this.consoleMenu = consoleMenu;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.print("Введите имя пользователя: ");
        String username = scanner.nextLine();

        User user = userService.getUserByUsername(username);
        if (user != null) {
            System.out.println("Добро пожаловать, " + user.getUsername() + "!");

            if ("admin".equals(user.getRole())) {
                adminConsoleMenu.start();
            } else {
                consoleMenu.start();
            }
        } else {
            System.out.println("Пользователь не найден!");
        }
    }
}

