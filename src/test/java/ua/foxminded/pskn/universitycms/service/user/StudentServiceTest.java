package ua.foxminded.pskn.universitycms.service.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.foxminded.pskn.universitycms.model.user.Student;
import ua.foxminded.pskn.universitycms.repository.university.StudentGroupRepository;
import ua.foxminded.pskn.universitycms.repository.user.StudentRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    private StudentService studentService;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentGroupRepository studentGroupRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        studentService = new StudentService(studentRepository, studentGroupRepository);
    }

    @Test
    public void testGetStudentByUserId() {
        Long userId = 1L;
        Student student = new Student();
        when(studentRepository.getStudentByUserId(userId)).thenReturn(student);

        Student retrievedStudent = studentService.getStudentByUserId(userId);

        assertEquals(student, retrievedStudent);
        verify(studentRepository).getStudentByUserId(userId);
    }

}