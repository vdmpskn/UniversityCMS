package ua.foxminded.pskn.universitycms.repository.university;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.foxminded.pskn.universitycms.model.university.Faculty;

import java.util.List;
import java.util.Optional;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    boolean deleteFacultyByFacultyName(String facultyName);

    Optional<Faculty> findByFacultyName(String facultyName);

    List<Faculty> findByUniversityId(Long universityId);

    @Modifying
    @Query("UPDATE Faculty f SET f.facultyName = :name WHERE f.facultyId = :id")
    void updateFacultyNameById(@Param("id") Long facultyId, @Param("name") String facultyName);


}
