package ua.foxminded.pskn.universitycms.repository.user;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import ua.foxminded.pskn.universitycms.model.user.Role;
import ua.foxminded.pskn.universitycms.model.user.Student;
import ua.foxminded.pskn.universitycms.model.user.User;

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
    private UserRepository userRepository;

    @Test
    void shouldGetStudentByUserId() {
        Role role = new Role();
        role.setRoleId(2);
        role.setName("student");

        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");
        user.setRole(role);
        user.setFacultyId(1L);

        user = userRepository.save(user);

        Student student = new Student();
        student.setUserId(user.getUserId());
        student.setGroupId(1L);
        student = studentRepository.save(student);

        Optional<Student> foundStudent = studentRepository.getStudentByUserId(user.getUserId());

        assertTrue(foundStudent.isPresent());

        assertEquals(user.getUserId(), foundStudent.get().getUserId());
    }

}
