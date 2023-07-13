package ua.foxminded.pskn.universitycms.service.university;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.foxminded.pskn.universitycms.model.university.StudentGroup;
import ua.foxminded.pskn.universitycms.repository.university.StudentGroupRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class StudentGroupServiceTest {

    private StudentGroupService studentGroupService;

    @Mock
    private StudentGroupRepository studentGroupRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        studentGroupService = new StudentGroupService(studentGroupRepository);
    }

    @Test
    void testSaveStudentGroup() {
        StudentGroup studentGroup = new StudentGroup();
        studentGroup.setGroupId(1L);
        when(studentGroupRepository.save(studentGroup)).thenReturn(studentGroup);

        StudentGroup savedStudentGroup = studentGroupService.saveStudentGroup(studentGroup);

        assertEquals(studentGroup, savedStudentGroup);
        verify(studentGroupRepository).save(studentGroup);
    }

    @Test
    void testGetStudentGroupById() {
        Long groupId = 1L;
        StudentGroup studentGroup = new StudentGroup();
        studentGroup.setGroupId(groupId);
        when(studentGroupRepository.findById(groupId)).thenReturn(Optional.of(studentGroup));

        StudentGroup retrievedStudentGroup = studentGroupService.getStudentGroupById(groupId);

        assertEquals(studentGroup, retrievedStudentGroup);
        verify(studentGroupRepository).findById(groupId);
    }

    @Test
    void testGetAllStudentGroups() {
        StudentGroup studentGroup1 = new StudentGroup();
        studentGroup1.setGroupId(1L);
        StudentGroup studentGroup2 = new StudentGroup();
        studentGroup2.setGroupId(2L);
        List<StudentGroup> studentGroups = Arrays.asList(studentGroup1, studentGroup2);
        when(studentGroupRepository.findAll()).thenReturn(studentGroups);

        List<StudentGroup> retrievedStudentGroups = studentGroupService.getAllStudentGroups();

        assertEquals(studentGroups, retrievedStudentGroups);
        verify(studentGroupRepository).findAll();
    }

    @Test
    void testDeleteStudentGroup() {
        Long groupId = 1L;

        studentGroupService.deleteStudentGroup(groupId);

        verify(studentGroupRepository).deleteById(groupId);
    }
}
