package ua.foxminded.pskn.universitycms.service.university;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.foxminded.pskn.universitycms.converter.studentgroup.StudentGroupDTOToStudentGroupConverter;
import ua.foxminded.pskn.universitycms.converter.studentgroup.StudentGroupToStudentGroupDTOConverter;
import ua.foxminded.pskn.universitycms.dto.StudentGroupDTO;
import ua.foxminded.pskn.universitycms.model.university.StudentGroup;
import ua.foxminded.pskn.universitycms.repository.university.StudentGroupRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StudentGroupServiceTest {
    @InjectMocks
    private StudentGroupService studentGroupService;

    @Mock
    private StudentGroupRepository studentGroupRepository;

    @Mock
    private StudentGroupDTOToStudentGroupConverter toStudentGroupConverter;

    @Mock
    private StudentGroupToStudentGroupDTOConverter toStudentGroupDTOConverter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveStudentGroup_ValidStudentGroup() {
        StudentGroupDTO studentGroupDTO = new StudentGroupDTO();
        studentGroupDTO.setStudentGroupName("Test Group");

        StudentGroup studentGroup = new StudentGroup();
        studentGroup.setGroupId(1L);
        studentGroup.setGroupName("Test Group");

        when(toStudentGroupConverter.convert(studentGroupDTO)).thenReturn(studentGroup);
        when(studentGroupRepository.save(studentGroup)).thenReturn(studentGroup);
        when(toStudentGroupDTOConverter.convert(studentGroup)).thenReturn(studentGroupDTO);

        StudentGroupDTO savedStudentGroupDTO = studentGroupService.saveStudentGroup(studentGroupDTO);

        assertNotNull(savedStudentGroupDTO);
        assertEquals("Test Group", savedStudentGroupDTO.getStudentGroupName());

        verify(toStudentGroupConverter).convert(studentGroupDTO);
        verify(studentGroupRepository).save(studentGroup);
        verify(toStudentGroupDTOConverter).convert(studentGroup);
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
