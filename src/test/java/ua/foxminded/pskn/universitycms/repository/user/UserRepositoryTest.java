package ua.foxminded.pskn.universitycms.repository.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import ua.foxminded.pskn.universitycms.model.user.Role;
import ua.foxminded.pskn.universitycms.model.user.User;

import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
            .facultyId(1L)
            .role(Role.builder().roleId(2).name("testRole").build())
            .userId(25L)
            .build();
        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByUsername(username);

        assertNotNull(foundUser);
        assertEquals(username, foundUser.get().getUsername());
    }

    @Test
     void shouldFindByUsername_NotFound() {
        Optional<User> foundUser = userRepository.findByUsername("nonexistentUser");

        assertFalse(foundUser.isPresent());
    }

    @Test
     void shouldFindProfessorByUsername() {
        Role professorRole = new Role(3,"professor");

        User professor = new User();
        professor.setUsername("professorUser");
        professor.setPassword("testPassword");
        professor.setRole(professorRole);
        professor = userRepository.save(professor);

        Optional<User> foundProfessor = userRepository.findProfessorByUsername(professor.getUsername());

        assertTrue(foundProfessor.isPresent());

        assertEquals(professor.getUsername(), foundProfessor.get().getUsername());
    }

    @Test
     void shouldFindProfessorByUsername_NotFound() {
        Optional<User> foundProfessor = userRepository.findProfessorByUsername("nonexistentProfessor");

        assertFalse(foundProfessor.isPresent());
    }

    @Test
    void shouldFindStudentByUsername() {
        Role studentRole = new Role(2,"student");
        User student = new User();
        student.setUsername("studentUser");
        student.setPassword("testPassword");
        student.setRole(studentRole);
        student = userRepository.save(student);

        Optional<User> foundStudent = userRepository.findStudentByUsername(student.getUsername());

        assertTrue(foundStudent.isPresent());

        assertEquals(student.getUsername(), foundStudent.get().getUsername());
    }

    @Test
    void shouldFindStudentByUsername_NotFound() {
        Optional<User> foundStudent = userRepository.findStudentByUsername("nonexistentStudent");

        assertFalse(foundStudent.isPresent());
    }

    @Test
     void shouldFindStudentByUserId() {
        Role studentRole = new Role(2,"student");

        User student = new User();
        student.setUsername("studentUser");
        student.setPassword("testPassword");
        student.setRole(studentRole);
        student = userRepository.save(student);


        Optional<User> foundStudent = userRepository.findStudentByUserId(student.getUserId());


        assertTrue(foundStudent.isPresent());

        assertEquals(student.getUserId(), foundStudent.get().getUserId());
    }

    @Test
     void shouldFindStudentByUserId_NotFound() {
        Optional<User> foundStudent = userRepository.findStudentByUserId(12345L);

        assertFalse(foundStudent.isPresent());
    }

    @Test
    void shouldFindProfessorByUserId() {
        Role professorRole = new Role(3,"professor");

        User professor = new User();
        professor.setUsername("professorUser");
        professor.setPassword("testPassword");
        professor.setRole(professorRole);
        professor = userRepository.save(professor);

        Optional<User> foundProfessor = userRepository.findProfessorByUserId(professor.getUserId());

        assertTrue(foundProfessor.isPresent());

        assertEquals(professor.getUsername(), foundProfessor.get().getUsername());
    }

    @Test
    void shouldFindProfessorById_NotFound() {
        Optional<User> foundProfessor = userRepository.findProfessorByUserId(54321L);

        assertFalse(foundProfessor.isPresent());
    }

    @Test
    void shouldFindAdminByUserId() {
        Role adminRole = new Role(1,"admin");

        User admin = new User();
        admin.setUsername("adminUser");
        admin.setPassword("testPassword");
        admin.setRole(adminRole);
        admin = userRepository.save(admin);

        Optional<User> foundAdmin = userRepository.findAdminByUserId(admin.getUserId());

        assertTrue(foundAdmin.isPresent());

        assertEquals(admin.getUsername(), foundAdmin.get().getUsername());
    }

    @Test
    void shouldFindAdminById_NotFound() {
        Optional<User> foundAdmin = userRepository.findAdminByUserId(54321L);

        assertFalse(foundAdmin.isPresent());
    }
}

