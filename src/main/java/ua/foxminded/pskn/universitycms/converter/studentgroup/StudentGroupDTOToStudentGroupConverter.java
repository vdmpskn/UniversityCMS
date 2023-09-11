package ua.foxminded.pskn.universitycms.converter.studentgroup;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.foxminded.pskn.universitycms.dto.StudentGroupDTO;
import ua.foxminded.pskn.universitycms.model.university.StudentGroup;

@Component
public class StudentGroupDTOToStudentGroupConverter implements Converter<StudentGroupDTO, StudentGroup> {
    @Override
    public StudentGroup convert(StudentGroupDTO studentGroupDTO) {
        return StudentGroup.builder()
            .groupId(studentGroupDTO.getStudentGroupId())
            .facultyId(studentGroupDTO.getFacultyID())
            .groupName(studentGroupDTO.getStudentGroupName())
            .build();
    }
}
