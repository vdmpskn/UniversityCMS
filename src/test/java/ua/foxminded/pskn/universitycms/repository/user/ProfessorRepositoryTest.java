package ua.foxminded.pskn.universitycms.repository.user;


import static org.junit.Assert.assertFalse;
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

import ua.foxminded.pskn.universitycms.model.user.Professor;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class ProfessorRepositoryTest {

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
        .withDatabaseName("test")
        .withUsername("test")
        .withPassword("test");

    @Autowired
    private ProfessorRepository professorRepository;

    @Test
    void shouldGetProfessorByUserId() {
        Professor professor = new Professor();
        professor.setUserId(1L);
        professor.setProfessorId(10L);
        professor = professorRepository.save(professor);

        Optional<Professor> foundProfessor = professorRepository.getProfessorByUserId(professor.getUserId());

        assertTrue(foundProfessor.isPresent());

        assertEquals(professor.getUserId(), foundProfessor.get().getUserId());
    }

    @Test
    void shouldGetProfessorByUserId_NotFound() {
        Optional<Professor> foundProfessor = professorRepository.getProfessorByUserId(999L);

        assertFalse(foundProfessor.isPresent());
    }

    @Test
     void shouldDeleteProfessorByUserId() {
        Professor professor = new Professor();
        professor.setUserId(2L);
        professor.setProfessorId(5L);
        professor = professorRepository.save(professor);

        professorRepository.deleteProfessorByUserId(professor.getUserId());

        Optional<Professor> foundProfessor = professorRepository.getProfessorByUserId(professor.getUserId());

        assertFalse(foundProfessor.isPresent());
    }

}

