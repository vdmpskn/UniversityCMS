package ua.foxminded.pskn.universitycms.repository.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.foxminded.pskn.universitycms.model.user.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
        .withDatabaseName("test")
        .withUsername("test")
        .withPassword("test");

    @Test
    void shouldFindByUsername() {
        String username = "testUser";
        User user = User.builder()
            .username("testUser")
            .password("pass")
            .facultyId(1)
            .roleId(2)
            .userId(25L)
            .build();
        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByUsername(username);

        assertNotNull(foundUser);
        assertEquals(username, foundUser.get().getUsername());
    }
}

