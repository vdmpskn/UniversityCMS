package ua.foxminded.pskn.universitycms.repository.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.foxminded.pskn.universitycms.model.user.Student;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class StudentRepositoryTest {

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
        .withDatabaseName("test")
        .withUsername("test")
        .withPassword("test");

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void shouldGetStudentByUserId() {
        Long userId = 10L;
        Student student = Student.builder()
            .groupId(1)
            .userId(userId)
            .build();
        entityManager.persistAndFlush(student);

        Student foundStudent = studentRepository.getStudentByUserId(userId);

        assertNotNull(foundStudent);
        assertEquals(userId, foundStudent.getUserId());
    }
}
