package ua.foxminded.pskn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PsknApplication {

    public static void main(final String[] args) {
        SpringApplication.run(PsknApplication.class, args);
    }

}
