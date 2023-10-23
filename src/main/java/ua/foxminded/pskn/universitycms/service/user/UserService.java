package ua.foxminded.pskn.universitycms.service.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ua.foxminded.pskn.universitycms.converter.user.UserConverter;
import ua.foxminded.pskn.universitycms.customexception.FacultyEditException;
import ua.foxminded.pskn.universitycms.customexception.ProfessorNotFoundException;
import ua.foxminded.pskn.universitycms.customexception.RoleNotFoundException;
import ua.foxminded.pskn.universitycms.customexception.StudentNotFoundException;
import ua.foxminded.pskn.universitycms.customexception.UserNotFoundException;
import ua.foxminded.pskn.universitycms.customexception.UserUpdateException;
import ua.foxminded.pskn.universitycms.dto.RoleDTO;
import ua.foxminded.pskn.universitycms.dto.UserDTO;
import ua.foxminded.pskn.universitycms.model.user.Professor;
import ua.foxminded.pskn.universitycms.model.user.Student;
import ua.foxminded.pskn.universitycms.model.user.User;
import ua.foxminded.pskn.universitycms.repository.user.ProfessorRepository;
import ua.foxminded.pskn.universitycms.repository.user.StudentRepository;
import ua.foxminded.pskn.universitycms.repository.user.UserRepository;


@RequiredArgsConstructor
@Slf4j
@Service
public class UserService {

    private final BCryptPasswordEncoder passwordEncoder;

    private final UserConverter userConverter;

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;

    private final RoleService roleService;

    public User getUserById(Long id) {
        log.debug("Getting user by ID: {}", id);
        return userRepository.findById(id).orElse(null);
    }

    public List<User> getAllUsers() {
        log.debug("Getting all users");
        return userRepository.findAll();
    }

    @Transactional
    public Page<UserDTO> getAllUsers(Pageable pageable) {
        log.debug("Retrieving all users with page number: {} and page size: {}",
            pageable.getPageNumber(),
            pageable.getPageSize());
        Page<User> userPage = userRepository.findAll(pageable);
        return userPage.map(userConverter::convertToDTO);
    }

    public Optional<User> getUserByUsername(String username) {
        log.debug("Getting user by username: {}", username);
        return userRepository.findByUsername(username);
    }

    public Optional<UserDTO> findAdminById(Long userId){
        if(userId==null){
            throw new IllegalArgumentException("Wrong value of userId");
        }
        log.debug("Getting admin by ID: {}", userId);

        Optional<User> admin = userRepository.findAdminByUserId(userId);

        if(admin.isEmpty()){
            throw new StudentNotFoundException("Admin not found!");
        }
        return admin.map(userConverter::convertToDTO);
    }


    public Optional<UserDTO> findProfessorByUsername(String username) {
        if (StringUtils.isBlank(username)) {
            throw new IllegalArgumentException("Wrong value of 'username' ");
        }
        log.debug("Getting admin by username: {}", username);
        Optional<User> professor = userRepository.findProfessorByUsername(username);

        if (professor.isEmpty()) {
            throw new ProfessorNotFoundException("User not found");
        }
        return professor.map(userConverter::convertToDTO);
    }

    public Optional<UserDTO> findProfessorById(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("Wrong value of userId");
        }

        log.debug("Getting professor by ID: {}", userId);

        return Optional.ofNullable(userRepository.findProfessorByUserId(userId))
            .map(professor -> professor.map(userConverter::convertToDTO))
            .orElseThrow(() -> new ProfessorNotFoundException("Professor not found"));
    }

    public Optional<UserDTO> findStudentById(Long userId){
        if(userId==null){
            throw new IllegalArgumentException("Wrong value of userId");
        }
        log.debug("Getting student by ID: {}", userId);

        return Optional.ofNullable(userRepository.findStudentByUserId(userId))
            .map(student -> student.map(userConverter::convertToDTO))
            .orElseThrow(() -> new StudentNotFoundException("Student not found"));
    }


    public UserDTO findStudentByUsername(String username) {
        if (StringUtils.isBlank(username)) {
            throw new IllegalArgumentException("Wrong value of 'username'");
        }

        log.debug("Getting student by username: {}", username);

        return (userRepository.findStudentByUsername(username))
            .map(userConverter::convertToDTO)
            .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public User saveStudent(UserDTO userDTO, Long groupId) {
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

    public void changeMyName(UserDTO userDTO, String newUsername) {
        if (StringUtils.isBlank(newUsername)) {
            throw new IllegalArgumentException("New username couldn't be blank.");
        }

        String currentUsername = userDTO.getUsername();
        log.info("Your current username is: {}", currentUsername);

        if (currentUsername.equals(newUsername)) {
            log.info("New username is equals to your current.");
            throw new UserUpdateException("New username is equals to your current.");
        }
        userRepository.save(userConverter.convertToEntity(userDTO.withUsername(newUsername)));
        log.info("Username changed successfully.");
    }

    public void changeMyFaculty(UserDTO userDTO, Long newFaculty) {
        Long currentFacultyId = userDTO.getFacultyId();
        log.info("Your current faculty ID is: {}", currentFacultyId);

        if (currentFacultyId.equals(newFaculty)) {
            log.info("New faculty is the same as your current faculty.");
            throw new FacultyEditException("New faculty is the same as your current faculty.");
        }
        userRepository.save(userConverter.convertToEntity(userDTO.withFacultyId(newFaculty)));
        log.info("Faculty ID changed successfully.");
    }

    public boolean changeStudentFaculty(Long userId, Long newFacultyId) {
        Optional<UserDTO> userDTO = findStudentById(userId);

        if (userDTO.isPresent()) {
            try {
                changeMyFaculty(userDTO.get(), newFacultyId);
                return true;
            } catch (FacultyEditException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean changeProfessorFaculty(Long userId, Long newFacultyId) {
        Optional<UserDTO> userDTO = findProfessorById(userId);

        if (userDTO.isPresent()) {
            try {
                changeMyFaculty(userDTO.get(), newFacultyId);
                return true;
            } catch (FacultyEditException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean changeUsername(Long userId, String newUsername) {
        Optional<UserDTO> userDTO = userRepository.findById(userId)
            .map(userConverter::convertToDTO);

        if (userDTO.isPresent()) {
            try{
                changeMyName(userDTO.get(), newUsername);
            }catch (IllegalArgumentException | UserUpdateException e){
                return false;
            }
            return true;
        }
        else {
            return false;
        }
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


    public void updateUser(UserDTO userDTO, Long roleId) {

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
            throw new UserUpdateException("User update exception!");
        }
    }

    public void createUserWithRole(UserDTO userDTO, Long groupId, Long roleId) {
        Optional<RoleDTO> role = roleService.findRoleById(roleId);

        role.ifPresent(roleDTO -> userDTO.setRoleDTO(RoleDTO.builder()
            .role(roleDTO.getRole())
            .build()));

        if (role.isPresent()) {
            switch (userDTO.getRoleDTO().getRole().getRoleId()) {
                case 1 -> saveAdmin(userDTO);
                case 2 -> saveStudent(userDTO, groupId);
                case 3 -> saveProfessor(userDTO);
                default -> throw new IllegalArgumentException("Invalid role: " + userDTO.getRoleDTO().getRole().getRoleId());
            }
        }
        else {
            log.warn("Role with ID not found.");
            throw new RoleNotFoundException("Role not found.");
        }

    }

    public boolean isOwnerOrAdmin(Long userId, Long requestUserId) {
        Optional<UserDTO> admin = findAdminById(userId);

        return admin.map(user -> user.getUserId().equals(requestUserId) || user.getUserId().equals(userId))
            .orElse(false);
    }

}
