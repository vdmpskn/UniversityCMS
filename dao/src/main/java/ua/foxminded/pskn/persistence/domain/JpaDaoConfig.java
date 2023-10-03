package ua.foxminded.pskn.persistence.domain;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@ComponentScan({"ua.foxminded.pskn.persistence.domain"}) //@Component/@Service
@EnableJpaRepositories({"ua.foxminded.pskn.persistence.domain"}) //@Repository
@EntityScan({"ua.foxminded.pskn.persistence.domain"}) //@Entity
@EnableTransactionManagement
public class JpaDaoConfig {
}
