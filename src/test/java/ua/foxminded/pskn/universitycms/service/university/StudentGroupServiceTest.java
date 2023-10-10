package ua.foxminded.pskn.universitycms.service.university;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ua.foxminded.pskn.universitycms.converter.studentgroup.StudentGroupConverter;
import ua.foxminded.pskn.universitycms.customexception.StudentGroupNotFoundException;
import ua.foxminded.pskn.universitycms.dto.StudentGroupDTO;
import ua.foxminded.pskn.universitycms.model.university.StudentGroup;
import ua.foxminded.pskn.universitycms.repository.university.StudentGroupRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StudentGroupServiceTest {
    @InjectMocks
    private StudentGroupService studentGroupService;

    @Mock
    private StudentGroupRepository studentGroupRepository;

    @Mock
    private StudentGroupConverter studentGroupConverter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSaveStudentGroup_ValidStudentGroup() {
        StudentGroupDTO studentGroupDTO = new StudentGroupDTO();
        studentGroupDTO.setGroupName("Test Group");

        StudentGroup mockStudentGroup = new StudentGroup();
        mockStudentGroup.setGroupId(1L);
        mockStudentGroup.setGroupName("Test Group");

        when(studentGroupConverter.convertToEntity(studentGroupDTO)).thenReturn(mockStudentGroup);

        when(studentGroupRepository.save(mockStudentGroup)).thenReturn(mockStudentGroup);

        StudentGroupDTO savedStudentGroupDTO = studentGroupService.saveStudentGroup(studentGroupDTO);

        assertNotNull(savedStudentGroupDTO);
        assertEquals("Test Group", savedStudentGroupDTO.getGroupName());

        verify(studentGroupConverter).convertToEntity(studentGroupDTO);

        verify(studentGroupRepository).save(mockStudentGroup);
    }


    @Test
    void shouldFailToSaveStudentGroup_BlankName() {
        StudentGroupDTO studentGroupDTO = new StudentGroupDTO();
        studentGroupDTO.setGroupName("");

        assertThrows(IllegalArgumentException.class, () -> studentGroupService.saveStudentGroup(studentGroupDTO));
    }

    @Test
    void shouldUpdateStudentGroupName_Success() {
        StudentGroupDTO studentGroupDTO = new StudentGroupDTO();
        studentGroupDTO.setGroupId(1L);
        studentGroupDTO.setGroupName("Group B");

        when(studentGroupRepository.existsById(eq(studentGroupDTO.getGroupId()))).thenReturn(true);

        assertDoesNotThrow(() -> studentGroupService.updateStudentGroupName(studentGroupDTO));

        verify(studentGroupRepository).updateStudentGroupName(eq(studentGroupDTO.getGroupId()), eq(studentGroupDTO.getGroupName()));
    }

    @Test
    void shouldFailToUpdateStudentGroupName_StudentGroupNotFound() {
        StudentGroupDTO studentGroupDTO = new StudentGroupDTO();
        studentGroupDTO.setGroupId(1L);
        studentGroupDTO.setGroupName("Group B");

        when(studentGroupRepository.existsById(eq(studentGroupDTO.getGroupId()))).thenReturn(false);

        assertThrows(StudentGroupNotFoundException.class, () -> studentGroupService.updateStudentGroupName(studentGroupDTO));
    }

    @Test
    void shouldFailToUpdateStudentGroupName_BlankName() {
        StudentGroupDTO studentGroupDTO = new StudentGroupDTO();
        studentGroupDTO.setGroupId(1L);
        studentGroupDTO.setGroupName("");

        assertThrows(IllegalArgumentException.class, () -> studentGroupService.updateStudentGroupName(studentGroupDTO));
    }

    @Test
    void shouldGetStudentGroupById() {
        Long groupId = 1L;
        StudentGroup studentGroup = new StudentGroup();
        studentGroup.setGroupId(groupId);
        when(studentGroupRepository.findById(groupId)).thenReturn(Optional.of(studentGroup));

        StudentGroup retrievedStudentGroup = studentGroupService.getStudentGroupById(groupId);

        assertEquals(studentGroup, retrievedStudentGroup);
        verify(studentGroupRepository).findById(groupId);
    }

    @Test
    void shouldGetAllStudentGroups() {
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
    void shouldDeleteStudentGroup() {
        Long groupId = 1L;

        studentGroupService.deleteStudentGroup(groupId);

        verify(studentGroupRepository).deleteById(groupId);
    }
}
