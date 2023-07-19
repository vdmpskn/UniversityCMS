package ua.foxminded.pskn.universitycms.repository.user;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.foxminded.pskn.universitycms.model.user.Professor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void shouldGetProfessorByUserId() {
        long userId = 10L;
        Professor professor = new Professor();
        professor.setUserId(userId);
        entityManager.persistAndFlush(professor);

        Professor foundProfessor = professorRepository.getProfessorByUserId(userId);

        assertNotNull(foundProfessor);
        assertEquals(userId, foundProfessor.getUserId());
    }

    @Test
    void shouldFindMaxProfessorId() {
        Professor professor1 = Professor.builder()
            .professorId(1)
            .build();
        professorRepository.save(professor1);

        Professor professor2 = Professor.builder()
            .professorId(2)
            .build();;
        professorRepository.save(professor2);

        Long maxProfessorId = professorRepository.findMaxProfessorId();

        assertNotNull(maxProfessorId);
        assertEquals(5L, maxProfessorId);
    }

}

