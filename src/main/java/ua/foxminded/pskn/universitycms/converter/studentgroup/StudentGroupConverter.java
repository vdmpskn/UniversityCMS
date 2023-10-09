package ua.foxminded.pskn.universitycms.converter.studentgroup;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ua.foxminded.pskn.universitycms.dto.StudentGroupDTO;
import ua.foxminded.pskn.universitycms.model.university.StudentGroup;

@Component
@RequiredArgsConstructor
public class StudentGroupConverter {
    private final ModelMapper modelMapper;

    public StudentGroupDTO convertToDTO(StudentGroup studentGroup){
        return modelMapper.map(studentGroup, StudentGroupDTO.class);
    }

    public StudentGroup convertToEntity(StudentGroupDTO studentGroupDTO){
        return modelMapper.map(studentGroupDTO, StudentGroup.class);
    }

}
