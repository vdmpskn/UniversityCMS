package ua.foxminded.pskn.universitycms.service.usercabinet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.foxminded.pskn.universitycms.model.university.StudentGroup;
import ua.foxminded.pskn.universitycms.model.user.Student;
import ua.foxminded.pskn.universitycms.model.user.User;
import ua.foxminded.pskn.universitycms.model.usercabinetdata.StudentCabinetData;
import ua.foxminded.pskn.universitycms.service.university.StudentGroupService;
import ua.foxminded.pskn.universitycms.service.user.StudentService;
import ua.foxminded.pskn.universitycms.service.user.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class UserCabinetServiceTest {

    @Mock
    private StudentGroupService studentGroupService;

    @Mock
    private UserService userService;

    @Mock
    private StudentService studentService;

    @InjectMocks
    private UserCabinetService userCabinetService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldGetStudentCabinetData_StudentFound() {
        User user = new User();
        user.setUserId(1L);
        user.setUsername("testUser");

        Student student = new Student();
        student.setUserId(1L);
        student.setGroupId(2);

        StudentGroup studentGroup = new StudentGroup();
        studentGroup.setGroupName("Group 2");

        List<StudentGroup> availableGroups = new ArrayList<>();
        availableGroups.add(studentGroup);

        when(userService.findStudentByUsername("testUser")).thenReturn(Optional.of(user));

        when(studentService.getStudentByUserId(1L)).thenReturn(Optional.of(student));

        when(studentGroupService.getStudentGroupById(2L)).thenReturn(studentGroup);

        when(studentGroupService.getAllStudentGroups()).thenReturn(availableGroups);

        StudentCabinetData cabinetData = userCabinetService.getStudentCabinetData("testUser");

        assertEquals("testUser", cabinetData.getUsername());
        assertEquals(1L, cabinetData.getStudentId());
        assertEquals("Group 2", cabinetData.getStudentGroup());
        assertEquals(availableGroups, cabinetData.getAvailableGroups());
    }

    @Test
    void shouldGetStudentCabinetData_UserNotFound() {
        when(userService.findStudentByUsername("nonexistentUser")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            userCabinetService.getStudentCabinetData("nonexistentUser");
        });
    }

    @Test
    void shouldGetStudentCabinetData_StudentNotFound() {
        User user = new User();
        user.setUserId(1L);
        user.setUsername("testUser");

        when(userService.findStudentByUsername("testUser")).thenReturn(Optional.of(user));

        when(studentService.getStudentByUserId(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            userCabinetService.getStudentCabinetData("testUser");
        });
    }
}

