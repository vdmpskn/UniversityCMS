package ua.foxminded.pskn.universitycms;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ua.foxminded.pskn.universitycms.front.AuthDemo;

@Component
public class AppRunner implements ApplicationRunner {

    private AuthDemo authDemo;

    public AppRunner(AuthDemo authDemo) {
        this.authDemo = authDemo;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        authDemo.start();
        System.exit(0);
    }
}
