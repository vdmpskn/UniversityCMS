package ua.foxminded.pskn.universitycms.service.usercabinet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ua.foxminded.pskn.universitycms.dto.StudentDTO;
import ua.foxminded.pskn.universitycms.dto.UserDTO;
import ua.foxminded.pskn.universitycms.model.university.StudentGroup;
import ua.foxminded.pskn.universitycms.model.usercabinetdata.StudentCabinetData;
import ua.foxminded.pskn.universitycms.service.university.StudentGroupService;
import ua.foxminded.pskn.universitycms.service.user.StudentService;
import ua.foxminded.pskn.universitycms.service.user.UserService;

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
        UserDTO user = new UserDTO();
        user.setUserId(1L);
        user.setUsername("testUser");

        StudentDTO student = new StudentDTO();
        student.setUserId(1L);
        student.setGroupId(2L);

        StudentGroup studentGroup = new StudentGroup();
        studentGroup.setGroupName("Group 2");

        List<StudentGroup> availableGroups = new ArrayList<>();
        availableGroups.add(studentGroup);

        when(userService.findStudentByUsername("testUser")).thenReturn(user);

        when(studentService.getStudentByUserId(1L)).thenReturn(student);

        when(studentGroupService.getStudentGroupById(2L)).thenReturn(studentGroup);

        when(studentGroupService.getAllStudentGroups()).thenReturn(availableGroups);

        StudentCabinetData cabinetData = userCabinetService.getStudentCabinetData("testUser");

        assertEquals("testUser", cabinetData.getUsername());
        assertEquals(1L, cabinetData.getStudentId());
        assertEquals("Group 2", cabinetData.getStudentGroup());
        assertEquals(availableGroups, cabinetData.getAvailableGroups());
    }

    @Test
    void shouldGetStudentCabinetData_StudentNotFound() {
        UserDTO user = new UserDTO();
        user.setUserId(1L);
        user.setUsername("testUser");

        when(userService.findStudentByUsername("testUser")).thenReturn(user);

        when(studentService.getStudentByUserId(1L)).thenReturn(null);

        assertThrows(NullPointerException.class, () -> {
            userCabinetService.getStudentCabinetData("testUser");
        });
    }
}

