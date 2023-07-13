package ua.foxminded.pskn.universitycms.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.foxminded.pskn.universitycms.model.user.Professor;
import ua.foxminded.pskn.universitycms.model.user.Student;
import ua.foxminded.pskn.universitycms.model.user.User;
import ua.foxminded.pskn.universitycms.repository.university.FacultyRepository;
import ua.foxminded.pskn.universitycms.repository.university.StudentGroupRepository;
import ua.foxminded.pskn.universitycms.repository.user.ProfessorRepository;
import ua.foxminded.pskn.universitycms.repository.user.StudentRepository;
import ua.foxminded.pskn.universitycms.repository.user.UserRepository;

import java.util.List;
import java.util.Scanner;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;

    Scanner scanner = new Scanner(System.in);


    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User saveStudent(User user){
        User savedUser = userRepository.save(user);
        if ("student".equals(user.getRole())) {
            Scanner scanner = new Scanner(System.in);
            log.info("Enter student group number: ");
            int groupId = scanner.nextInt();

            Student student = Student.builder()
                    .userId(savedUser.getUserId())
                    .groupId(groupId)
                    .build();
            student.setUserId(savedUser.getUserId());
            student.setGroupId(groupId);
            studentRepository.save(student);
        }
        return savedUser;
    }

    public User saveProfessor(User user){
        User savedUser = userRepository.save(user);
        if ("professor".equals(user.getRole())) {
            Scanner scanner = new Scanner(System.in);

            Professor professor = new Professor();
            professor.setUserId(savedUser.getUserId());
            professorRepository.save(professor);
        }
        return savedUser;
    }

    public User saveAdmin(User user){
        User savedUser = userRepository.save(user);
        User admin = User.builder()
                .userId(savedUser.getUserId())
                .build();
            userRepository.save(admin);
        return savedUser;
    }

    public void changeMyName(User user){
        String username = user.getUsername();
        log.info("You actual username is " + username);
        log.info("Write your new username");
        String changedUsername = scanner.nextLine();
        user.setUsername(changedUsername);
        userRepository.save(user);
    }

    public void changeMyFaculty(User user){
        log.info(facultyRepository.findAll().toString());
        int facultyId = user.getFacultyId();
        log.info("Your actual faculty ID is: " + facultyId);
        log.info("Write your new faculty ID ");
        int changedFacultyId = scanner.nextInt();
        user.setFacultyId(changedFacultyId);
        userRepository.save(user);

    }

    public void deleteUser(Long id){
         userRepository.deleteById(id);
    }
}
