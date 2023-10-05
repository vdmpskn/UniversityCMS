package ua.foxminded.pskn.universitycms.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.pskn.universitycms.converter.user.UserConverter;
import ua.foxminded.pskn.universitycms.dto.RoleDTO;
import ua.foxminded.pskn.universitycms.dto.UserDTO;
import ua.foxminded.pskn.universitycms.model.user.Professor;
import ua.foxminded.pskn.universitycms.model.user.Role;
import ua.foxminded.pskn.universitycms.model.user.Student;
import ua.foxminded.pskn.universitycms.model.user.User;
import ua.foxminded.pskn.universitycms.repository.university.FacultyRepository;
import ua.foxminded.pskn.universitycms.repository.user.ProfessorRepository;
import ua.foxminded.pskn.universitycms.repository.user.StudentRepository;
import ua.foxminded.pskn.universitycms.repository.user.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserService {

    private final BCryptPasswordEncoder passwordEncoder;

    private final UserConverter userConverter;

    private final UserRepository userRepository;
    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;

    private final RoleService roleService;

    private final Scanner scanner = new Scanner(System.in);

    public User getUserById(Long id) {
        log.debug("Getting user by ID: {}", id);
        return userRepository.findById(id).orElse(null);
    }

    public List<User> getAllUsers() {
        log.debug("Getting all users");
        return userRepository.findAll();
    }

    public Page<UserDTO> getAllUsers(Pageable pageable) {
        log.debug("Retrieving all users with page number: {} and page size: {}", pageable.getPageNumber(), pageable.getPageSize());
        Page<User> userPage = userRepository.findAll(pageable);
        return userPage.map(userConverter::convertToDTO);
    }

    public Optional<User> getUserByUsername(String username) {
        log.debug("Getting user by username: {}", username);
        return userRepository.findByUsername(username);
    }

    public Optional<User> findAdminByUsername(String username) {
        log.debug("Getting admin by username: {}", username);
        return userRepository.findAdminByUsername(username);
    }

    public Optional<User> findProfessorByUsername(String username) {
        log.debug("Getting admin by username: {}", username);
        return userRepository.findProfessorByUsername(username);
    }

    public Optional<User> findStudentByUsername(String username) {
        log.debug("Getting admin by username: {}", username);
        return userRepository.findStudentByUsername(username);
    }

    public User saveStudent(UserDTO userDTO, int groupId) {
        User user = userConverter.convertToEntity(userDTO);
        log.info("Saving student: {}", user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        Student student = Student.builder()
            .userId(savedUser.getUserId())
            .groupId(groupId)
            .build();
        student.setUserId(savedUser.getUserId());
        student.setGroupId(groupId);
        studentRepository.save(student);
        return savedUser;
    }

    public User saveProfessor(UserDTO userDTO) {
        User user = userConverter.convertToEntity(userDTO);
        log.info("Saving professor: {}", user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        Professor professor = new Professor();
        professor.setUserId(savedUser.getUserId());
        professorRepository.save(professor);
        return savedUser;
    }

    public User saveAdmin(UserDTO userDTO) {
        User user = userConverter.convertToEntity(userDTO);
        log.info("Saving admin: {}", user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        User admin = User.builder()
            .userId(savedUser.getUserId())
            .username(savedUser.getUsername())
            .password(savedUser.getPassword())
            .role(savedUser.getRole())
            .facultyId(savedUser.getFacultyId())
            .build();
        userRepository.save(admin);
        return savedUser;
    }

    public void changeMyName(User user) {
        String username = user.getUsername();
        log.info("Your actual username is: {}", username);
        log.info("Write your new username: ");
        String changedUsername = scanner.nextLine();
        user.setUsername(changedUsername);
        userRepository.save(user);
        log.info("Username changed successfully.");
    }

    public void changeMyFaculty(User user) {
        log.info(facultyRepository.findAll().toString());
        int facultyId = user.getFacultyId();
        log.info("Your actual faculty ID is: {}", facultyId);
        log.info("Write your new faculty ID: ");
        int changedFacultyId = scanner.nextInt();
        user.setFacultyId(changedFacultyId);
        userRepository.save(user);
        log.info("Faculty ID changed successfully.");
    }

    @Transactional
    public void deleteUser(UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findById(userDTO.getUserId());

        if (userOptional.isPresent()) {
            switch (userDTO.getRoleDTO().getRole().getRoleId()) {
                case 1 -> userRepository.deleteById(userDTO.getUserId());
                case 3 -> professorRepository.deleteProfessorByUserId(userDTO.getUserId());
                case 2 -> studentRepository.deleteById(userDTO.getUserId());
                default -> throw new IllegalArgumentException("Invalid role: " + userDTO.getRoleDTO().getRole().getRoleId());
            }
        }
        else {
            log.warn("User with ID {} not found.", userDTO.getUserId());
        }
        log.info("Deleting user with ID: {}", userDTO.getUserId());
    }


    public void updateUser(UserDTO userDTO, int roleId) {

        Optional<RoleDTO> role = roleService.findRoleById(roleId);
        userDTO.setRoleDTO(RoleDTO.builder()
            .role(role.get().getRole())
            .build());

        Optional<User> userOptional = userRepository.findById(userDTO.getUserId());

        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();

            existingUser.setUsername(userDTO.getUsername());
            existingUser.setRole(userDTO.getRoleDTO().getRole());
            existingUser.setFacultyId(userDTO.getFacultyId());
            existingUser.setUserId(userDTO.getUserId());

            userRepository.save(existingUser);

            log.info("User updated: {}", existingUser);
        }
        else {
            log.warn("User with ID {} not found.", userDTO.getUserId());
        }
    }

    public void createUserWithRole(UserDTO userDTO, int groupId, int roleId) {
        Optional<RoleDTO> role = roleService.findRoleById(roleId);
        userDTO.setRoleDTO(RoleDTO.builder()
            .role(role.get().getRole())
            .build());

        if(role.isPresent()){
            switch (userDTO.getRoleDTO().getRole().getRoleId()) {
                case 1 -> saveAdmin(userDTO);
                case 2 -> saveStudent(userDTO, groupId);
                case 3 -> saveProfessor(userDTO);
                default -> throw new IllegalArgumentException("Invalid role: " + userDTO.getRoleDTO().getRole().getRoleId());
            }
        } else {
            log.warn("Role with ID {} not found.", role.get().getRole().getRoleId());
        }

    }
}
