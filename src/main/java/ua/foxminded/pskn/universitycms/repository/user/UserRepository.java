package ua.foxminded.pskn.universitycms.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.foxminded.pskn.universitycms.model.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.username = :username AND u.role = 'admin'")
    User findAdminByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.username = :username AND u.role = 'professor'")
    User findProfessorByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.username = :username AND u.role = 'student'")
    User findStudentByUsername(String username);
}
