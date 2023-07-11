package ua.foxminded.pskn.universitycms.service;

import org.springframework.stereotype.Service;
import ua.foxminded.pskn.universitycms.model.user.Professor;
import ua.foxminded.pskn.universitycms.model.user.Student;
import ua.foxminded.pskn.universitycms.model.user.User;
import ua.foxminded.pskn.universitycms.repository.user.ProfessorRepository;
import ua.foxminded.pskn.universitycms.repository.user.StudentRepository;
import ua.foxminded.pskn.universitycms.repository.user.UserRepository;

import java.util.List;
import java.util.Scanner;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final StudentRepository studentRepository;

    private final ProfessorRepository professorRepository;

    public UserService(UserRepository userRepository, StudentRepository studentRepository, ProfessorRepository professorRepository) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.professorRepository = professorRepository;
    }

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
            System.out.print("Enter student group number: ");
            int groupId = scanner.nextInt();

            Student student = new Student();
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
            User admin = new User();
            admin.setUserId(savedUser.getUserId());
            userRepository.save(admin);
        return savedUser;
    }

    public void deleteUser(Long id){
         userRepository.deleteById(id);
    }
}
