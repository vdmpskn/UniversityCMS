package ua.foxminded.pskn.universitycms.repository.university;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.foxminded.pskn.universitycms.model.university.University;

import java.util.Optional;

@Repository
public interface UniversityRepository extends JpaRepository<University, Long> {
    boolean deleteUniversityByUniversityName(String universityName);

    Optional<University> findByUniversityName(String universityName);
}

