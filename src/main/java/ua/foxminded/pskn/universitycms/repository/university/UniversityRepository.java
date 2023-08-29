package ua.foxminded.pskn.universitycms.repository.university;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.foxminded.pskn.universitycms.model.university.University;

import java.util.Optional;

@Repository
public interface UniversityRepository extends JpaRepository<University, Long> {
   Optional<University> findByUniversityName(String universityName);

    boolean existsByUniversityId(int universityId);

    @Modifying
    @Query("UPDATE University u SET u.universityName = :name WHERE u.universityId = :id")
    void updateUniversityName(@Param("id") Long id, @Param("name") String name);
}

