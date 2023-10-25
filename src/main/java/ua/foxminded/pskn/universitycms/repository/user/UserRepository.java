package ua.foxminded.pskn.universitycms.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.foxminded.pskn.universitycms.model.user.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.username = :username AND u.role.roleId = 3")
    Optional<User> findProfessorByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.username = :username AND u.role.roleId = 2")
    Optional<User> findStudentByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.userId = :userId AND u.role.roleId = 2")
    Optional<User> findStudentByUserId(Long userId);

    @Query("SELECT u FROM User u WHERE u.userId = :userId AND u.role.roleId = 3")
    Optional<User> findProfessorByUserId(Long userId);

    @Query("SELECT u FROM User u WHERE u.userId = :userId AND u.role.roleId = 1")
    Optional<User> findAdminByUserId(Long userId);
}
