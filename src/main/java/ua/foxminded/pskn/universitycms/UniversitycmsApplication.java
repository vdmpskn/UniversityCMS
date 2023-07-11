package ua.foxminded.pskn.universitycms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ua.foxminded.pskn.universitycms.front.AuthDemo;


@SpringBootApplication
public class UniversitycmsApplication implements CommandLineRunner {

    @Autowired
    private AuthDemo authDemo;

    public static void main(String[] args) {
        SpringApplication.run(UniversitycmsApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        authDemo.start();
        System.exit(0);
    }

}
