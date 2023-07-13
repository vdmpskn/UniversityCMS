package ua.foxminded.pskn.universitycms.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.foxminded.pskn.universitycms.model.user.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student getStudentByUserId(Long userId);
}
