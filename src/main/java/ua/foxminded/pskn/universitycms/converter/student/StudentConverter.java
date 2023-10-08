package ua.foxminded.pskn.universitycms.converter.student;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ua.foxminded.pskn.universitycms.dto.StudentDTO;
import ua.foxminded.pskn.universitycms.model.user.Student;

@Component
@RequiredArgsConstructor
public class StudentConverter {
    private final ModelMapper modelMapper;

    public StudentDTO convertToDTO(Student student){return modelMapper.map(student, StudentDTO.class);}

    public Student convertToEntity(StudentDTO studentDTO){return modelMapper.map(studentDTO, Student.class);}
}
