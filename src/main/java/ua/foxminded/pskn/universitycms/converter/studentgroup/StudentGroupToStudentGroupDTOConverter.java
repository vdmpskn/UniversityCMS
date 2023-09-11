package ua.foxminded.pskn.universitycms.converter.studentgroup;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.foxminded.pskn.universitycms.dto.StudentGroupDTO;
import ua.foxminded.pskn.universitycms.model.university.StudentGroup;

@Component
public class StudentGroupToStudentGroupDTOConverter implements Converter<StudentGroup, StudentGroupDTO> {
    @Override
    public StudentGroupDTO convert(StudentGroup studentGroup) {
        return StudentGroupDTO.builder()
            .studentGroupId(studentGroup.getGroupId())
            .facultyID(studentGroup.getFacultyId())
            .studentGroupName(studentGroup.getGroupName())
            .build();
    }
}
