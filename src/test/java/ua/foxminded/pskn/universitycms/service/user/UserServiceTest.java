package ua.foxminded.pskn.universitycms.service.user;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ua.foxminded.pskn.universitycms.converter.user.UserConverter;
import ua.foxminded.pskn.universitycms.customexception.FacultyEditException;
import ua.foxminded.pskn.universitycms.customexception.ProfessorNotFoundException;
import ua.foxminded.pskn.universitycms.customexception.UserNotFoundException;
import ua.foxminded.pskn.universitycms.customexception.UserUpdateException;
import ua.foxminded.pskn.universitycms.dto.RoleDTO;
import ua.foxminded.pskn.universitycms.dto.UserDTO;
import ua.foxminded.pskn.universitycms.model.user.Professor;
import ua.foxminded.pskn.universitycms.model.user.Role;
import ua.foxminded.pskn.universitycms.model.user.Student;
import ua.foxminded.pskn.universitycms.model.user.User;
import ua.foxminded.pskn.universitycms.repository.user.ProfessorRepository;
import ua.foxminded.pskn.universitycms.repository.user.StudentRepository;
import ua.foxminded.pskn.universitycms.repository.user.UserRepository;


class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserConverter userConverter;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private ProfessorRepository professorRepository;

    @Mock
    private RoleService roleService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetAllUsers_Pageable() {
        Pageable pageable = PageRequest.of(0, 10);

        List<User> userList = new ArrayList<>();
        List<UserDTO> userDTOList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setUserId((long) i);
            userList.add(user);

            UserDTO userDTO = new UserDTO();
            userDTO.setUserId((long) i);
            userDTOList.add(userDTO);
        }

        when(userRepository.findAll(pageable)).thenReturn(new PageImpl<>(userList, pageable, 10));

        when(userConverter.convertToDTO(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            return userDTOList.stream()
                .filter(dto -> dto.getUserId().equals(user.getUserId()))
                .findFirst()
                .orElse(null);
        });

        Page<UserDTO> result = userService.getAllUsers(pageable);

        assertEquals(userDTOList, result.getContent());
    }

    @Test
    void shouldGetUserByUsername() {
        String username = "testUser";

        User user = new User();
        user.setUserId(1L);
        user.setUsername(username);
        user.setPassword("password");

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserByUsername(username);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void shouldFindAdminById_Success() {
        Long userId = 1L;

        User adminUser = new User();
        UserDTO adminUserDTO = new UserDTO();
        adminUser.setUserId(userId);
        adminUserDTO.setUserId(userId);

        when(userRepository.findAdminByUserId(userId)).thenReturn(Optional.of(adminUser));
        when(userConverter.convertToDTO(adminUser)).thenReturn(adminUserDTO);

        Optional<UserDTO> result = userService.findAdminById(userId);

        assertTrue(result.isPresent());
        assertEquals(adminUserDTO, result.get());
    }

    @Test
    void shouldFindAdminById_NullUserId() {
        Long userId = null;

        assertThrows(IllegalArgumentException.class, () -> {
            userService.findAdminById(userId);
        });
    }


    @Test
    void shouldFindProfessorByUsername_Success() {
        String username = "professorUser";

        User professorUser = new User();
        UserDTO professorUserDTO = new UserDTO();
        professorUser.setUsername(username);
        professorUserDTO.setUsername(username);

        when(userRepository.findProfessorByUsername(username)).thenReturn(Optional.of(professorUser));
        when(userConverter.convertToDTO(professorUser)).thenReturn(professorUserDTO);

        Optional<UserDTO> result = userService.findProfessorByUsername(username);

        assertTrue(result.isPresent());
        assertEquals(professorUserDTO, result.get());
    }


    @Test
    void shouldFindProfessorByUsername_NotFound() {
        String username = "nonExistentProfessor";

        when(userRepository.findProfessorByUsername(username)).thenReturn(Optional.empty());

        assertThrows(ProfessorNotFoundException.class, () -> {
            userService.findProfessorByUsername(username);
        });
    }

    @Test
    void shouldFindProfessorByUsername_BlankUsername() {
        String username = "";

        assertThrows(IllegalArgumentException.class, () -> {
            userService.findProfessorByUsername(username);
        });
    }

    @Test
    void shouldFindProfessorById_Success() {
        Long userId = 1L;

        User professorUser = new User();
        UserDTO professorUserDTO = new UserDTO();
        professorUser.setUserId(userId);
        professorUserDTO.setUserId(userId);

        when(userRepository.findProfessorByUserId(userId)).thenReturn(Optional.of(professorUser));
        when(userConverter.convertToDTO(professorUser)).thenReturn(professorUserDTO);

        Optional<UserDTO> result = userService.findProfessorById(userId);

        assertTrue(result.isPresent());
        assertEquals(professorUserDTO, result.get());
    }

    @Test
    void shouldFindProfessorById_NullUserId() {
        Long userId = null;

        assertThrows(IllegalArgumentException.class, () -> userService.findProfessorById(userId));
    }

    @Test
    void shouldFindStudentById_Success() {
        Long userId = 1L;

        User studentUser = new User();
        UserDTO studentUserDTO = new UserDTO();
        studentUser.setUserId(userId);
        studentUserDTO.setUserId(userId);

        when(userRepository.findStudentByUserId(userId)).thenReturn(Optional.of(studentUser));
        when(userConverter.convertToDTO(studentUser)).thenReturn(studentUserDTO);

        Optional<UserDTO> result = userService.findStudentById(userId);

        assertTrue(result.isPresent());
        assertEquals(studentUserDTO, result.get());
    }

    @Test
    void shouldFindStudentById_NullUserId() {
        Long userId = null;

        assertThrows(IllegalArgumentException.class, () -> userService.findStudentById(userId));
    }

    @Test
    void shouldFindStudentByUsername_Success() {
        String username = "testStudent";

        User studentUser = new User();
        UserDTO studentUserDTO = new UserDTO();
        studentUser.setUsername(username);
        studentUserDTO.setUsername(username);

        when(userRepository.findStudentByUsername(username)).thenReturn(Optional.of(studentUser));
        when(userConverter.convertToDTO(studentUser)).thenReturn(studentUserDTO);

        UserDTO result = userService.findStudentByUsername(username);

        assertNotNull(result);
        assertEquals(studentUserDTO, result);
    }

    @Test
    void shouldFindStudentByUsername_BlankUsername() {
        String username = "  ";

        assertThrows(IllegalArgumentException.class, () -> userService.findStudentByUsername(username));
    }

    @Test
    void shouldFindStudentByUsername_UserNotFound() {
        String username = "nonExistentUser";

        when(userRepository.findStudentByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findStudentByUsername(username));
    }

    @Test
    void shouldChangeMyName_Success() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("oldUsername");
        String newUsername = "newUsername";

        when(userConverter.convertToEntity(userDTO.withUsername(newUsername))).thenReturn(new User());

        userService.changeMyName(userDTO, newUsername);

        verify(userConverter).convertToEntity(userDTO.withUsername(newUsername));
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldChangeMyName_WithBlankNewUsername() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("currentUsername");
        String newUsername = "";


        assertThrows(IllegalArgumentException.class, () -> userService.changeMyName(userDTO, newUsername));
    }

    @Test
     void shouldChangeMyName_WithSameUsername() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("currentUsername");
        String newUsername = "currentUsername";

        assertThrows(UserUpdateException.class, () -> userService.changeMyName(userDTO, newUsername));
    }


    @Test
    void shouldSaveStudent_Success() {
        UserDTO userDTO = new UserDTO();

        Long groupId = 1L;

        User user = new User();

        when(userConverter.convertToEntity(userDTO)).thenReturn(user);
        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
        when(userRepository.save(user)).thenReturn(user);

        Student student = Student.builder()
            .userId(user.getUserId())
            .groupId(groupId)
            .build();

        userService.saveStudent(userDTO, groupId);

        verify(userConverter).convertToEntity(userDTO);
        verify(passwordEncoder).encode(user.getPassword());
        verify(userRepository).save(user);
        verify(studentRepository).save(student);
    }

    @Test
    void shouldSaveProfessor_Success() {
        UserDTO userDTO = new UserDTO();

        User user = new User();

        when(userConverter.convertToEntity(userDTO)).thenReturn(user);
        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
        when(userRepository.save(user)).thenReturn(user);

        Professor professor = new Professor();
        professor.setUserId(user.getUserId());

        userService.saveProfessor(userDTO);

        verify(userConverter).convertToEntity(userDTO);
        verify(passwordEncoder).encode(user.getPassword());
        verify(userRepository).save(user);
        verify(professorRepository).save(professor);
    }

    @Test
    void shouldSaveAdmin_Success() {
        UserDTO userDTO = new UserDTO();

        User user = new User();

        when(userConverter.convertToEntity(userDTO)).thenReturn(user);
        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
        when(userRepository.save(user)).thenReturn(user);

        User admin = User.builder()
            .userId(user.getUserId())
            .username(user.getUsername())
            .password(user.getPassword())
            .role(user.getRole())
            .facultyId(user.getFacultyId())
            .build();

        userService.saveAdmin(userDTO);

        verify(userConverter).convertToEntity(userDTO);
        verify(passwordEncoder).encode(user.getPassword());
        verify(userRepository, times(2)).save(user);
        verify(userRepository, times(2)).save(admin);
    }

    @Test
    void shouldChangeMyFaculty_Success() {
        UserDTO userDTO = new UserDTO();
        userDTO.setFacultyId(1L);
        Long newFaculty = 2L;

        when(userConverter.convertToEntity(userDTO.withFacultyId(newFaculty))).thenReturn(new User());

        userService.changeMyFaculty(userDTO, newFaculty);

        verify(userConverter).convertToEntity(userDTO.withFacultyId(newFaculty));
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldChangeMyFaculty_WithSameFaculty() {
        UserDTO userDTO = new UserDTO();
        userDTO.setFacultyId(1L);
        Long newFaculty = 1L;

        assertThrows(FacultyEditException.class, () -> userService.changeMyFaculty(userDTO, newFaculty));
    }


    @Test
    void shouldDeleteUser_WithRoleAdmin() {
        Long userId = 1L;
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(userId);
        userDTO.setRoleDTO(new RoleDTO(new Role(1, "admin"), "RoleName"));

        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));

        userService.deleteUser(userDTO);

        verify(userRepository, times(1)).deleteById(userId);
        verify(studentRepository, never()).deleteById(userId);
        verify(professorRepository, never()).deleteProfessorByUserId(userId);
    }

    @Test
    void shouldDeleteUser_WithRoleStudent() {
        Long userId = 1L;
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(userId);
        userDTO.setRoleDTO(new RoleDTO(new Role(2, "student"), "RoleName"));

        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));

        userService.deleteUser(userDTO);

        verify(userRepository, never()).deleteById(userId);
        verify(studentRepository, times(1)).deleteById(userId);
        verify(professorRepository, never()).deleteProfessorByUserId(userId);
    }

    @Test
    void shouldDeleteUser_WithRoleProfessor() {
        Long userId = 1L;
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(userId);
        userDTO.setRoleDTO(new RoleDTO(new Role(3, "professor"), "RoleName"));

        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));

        userService.deleteUser(userDTO);

        verify(userRepository, never()).deleteById(userId);
        verify(studentRepository, never()).deleteById(userId);
        verify(professorRepository, times(1)).deleteProfessorByUserId(userId);
    }

    @Test
    void shouldDeleteUser_WithUserNotFound() {
        Long userId = 1L;
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(userId);
        userDTO.setRoleDTO(new RoleDTO(new Role(1, "admin"), "RoleName"));

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        userService.deleteUser(userDTO);

        verify(userRepository, never()).deleteById(userId);
        verify(studentRepository, never()).deleteById(userId);
        verify(professorRepository, never()).deleteProfessorByUserId(userId);
    }

    @Test
    void shouldUpdateUser_Success() {
        UserDTO userDTO = new UserDTO();
        Long roleId = 1L;

        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setRole(new Role());

        User existingUser = new User();

        Mockito.when(roleService.findRoleById(roleId)).thenReturn(Optional.of(roleDTO));

        Mockito.when(userRepository.findById(userDTO.getUserId())).thenReturn(Optional.of(existingUser));

        userService.updateUser(userDTO, roleId);

        Mockito.verify(userRepository, Mockito.times(1)).save(existingUser);
    }

    @Test
     void shouldUpdateUser_UserNotFound() {
        UserDTO userDTO = new UserDTO();
        Long roleId = 1L;

        RoleDTO roleDTO = new RoleDTO();
        Mockito.when(roleService.findRoleById(roleId)).thenReturn(Optional.of(roleDTO));

        Mockito.when(userRepository.findById(userDTO.getUserId())).thenReturn(Optional.empty());

        assertThrows(UserUpdateException.class, () -> userService.updateUser(userDTO, roleId));
    }
}
