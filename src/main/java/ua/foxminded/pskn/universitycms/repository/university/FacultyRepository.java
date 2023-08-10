package ua.foxminded.pskn.universitycms.repository.university;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.foxminded.pskn.universitycms.model.university.Faculty;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    void deleteFacultyByFacultyName(String facultyName);
}
