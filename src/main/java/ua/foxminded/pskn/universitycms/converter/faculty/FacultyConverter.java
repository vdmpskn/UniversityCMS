package ua.foxminded.pskn.universitycms.converter.faculty;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ua.foxminded.pskn.universitycms.dto.FacultyDTO;
import ua.foxminded.pskn.universitycms.model.university.Faculty;


@Component
@RequiredArgsConstructor
public class FacultyConverter {

    private final ModelMapper modelMapper;

    public FacultyDTO convertToDTO(Faculty faculty) {
        return modelMapper.map(faculty, FacultyDTO.class);
    }

    public Faculty convertToEntity(FacultyDTO facultyDTO) {
        return modelMapper.map(facultyDTO, Faculty.class);
    }
}
