package ua.foxminded.pskn.universitycms.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.foxminded.pskn.universitycms.model.user.Professor;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
}
