package ua.foxminded.pskn.universitycms.service.user;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ua.foxminded.pskn.universitycms.converter.student.StudentConverter;
import ua.foxminded.pskn.universitycms.customexception.StudentGroupNotFoundException;
import ua.foxminded.pskn.universitycms.customexception.StudentNotFoundException;
import ua.foxminded.pskn.universitycms.dto.StudentDTO;
import ua.foxminded.pskn.universitycms.model.university.StudentGroup;
import ua.foxminded.pskn.universitycms.model.user.Student;
import ua.foxminded.pskn.universitycms.repository.university.StudentGroupRepository;
import ua.foxminded.pskn.universitycms.repository.user.StudentRepository;


class StudentServiceTest {

    @InjectMocks
    private StudentService studentService;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentGroupRepository studentGroupRepository;

    @Mock
    private StudentConverter studentConverter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetStudentByUserId_Success() {
        Long userId = 1L;

        Student mockStudent = new Student();
        StudentDTO mockStudentDTO = new StudentDTO();

        when(studentRepository.getStudentByUserId(userId)).thenReturn(Optional.of(mockStudent));

        when(studentConverter.convertToDTO(mockStudent)).thenReturn(mockStudentDTO);

        StudentDTO result = studentService.getStudentByUserId(userId);

        Assertions.assertNotNull(result);
        assertEquals(mockStudentDTO, result);

        verify(studentRepository).getStudentByUserId(userId);

        verify(studentConverter).convertToDTO(mockStudent);
    }

    @Test
     void shouldGetStudentByUserId_UserIdIsNull() {
        Long userId = null;

        assertThrows(IllegalArgumentException.class, () -> {
            studentService.getStudentByUserId(userId);
        });
    }

    @Test
    void shouldGetStudentByUserId_UserIdIsNegative() {
        Long userId = -1L;

        assertThrows(IllegalArgumentException.class, () -> {
            studentService.getStudentByUserId(userId);
        });
    }

    @Test
    void shouldGetStudentByUserId_StudentNotFound() {
        Long userId = 1L;

        when(studentRepository.getStudentByUserId(userId)).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> {
            studentService.getStudentByUserId(userId);
        });
    }


    @Test
    void shouldChangeStudentGroup_Success() {
        Long studentId = 1L;
        Long newGroupId = 2L;

        when(studentGroupRepository.findById(newGroupId)).thenReturn(Optional.of(new StudentGroup()));

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(new Student()));

        studentService.changeMyGroup(studentId, newGroupId);

        verify(studentGroupRepository).findById(newGroupId);

        verify(studentRepository).findById(studentId);
    }

    @Test
    void shouldChangeMyGroup_GroupNotFound() {
        Long studentId = 1L;
        Long newGroupId = 2L;

        when(studentGroupRepository.findById(newGroupId)).thenReturn(Optional.empty());

        StudentGroupNotFoundException exception = assertThrows(StudentGroupNotFoundException.class, () -> {
            studentService.changeMyGroup(studentId, newGroupId);
        });

        assertEquals("Group with ID 2 not found.", exception.getMessage());
    }

    @Test
     void shouldChangeMyGroup_StudentNotFound() {
        Long studentId = 1L;
        Long newGroupId = 2L;

        when(studentGroupRepository.findById(newGroupId)).thenReturn(Optional.of(new StudentGroup()));

        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        StudentNotFoundException exception = assertThrows(StudentNotFoundException.class, () -> {
            studentService.changeMyGroup(studentId, newGroupId);
        });

        assertEquals("Student with ID 1 not found.", exception.getMessage());
    }

}
