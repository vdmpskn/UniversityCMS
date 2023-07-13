package ua.foxminded.pskn.universitycms;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ua.foxminded.pskn.universitycms.front.AuthDemo;

@RequiredArgsConstructor
@Component
public class AppRunner implements ApplicationRunner {

    private final AuthDemo authDemo;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        authDemo.start();
        System.exit(0);
    }
}
