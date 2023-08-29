package ua.foxminded.pskn.universitycms.converter.faculty;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.foxminded.pskn.universitycms.dto.FacultyDTO;
import ua.foxminded.pskn.universitycms.model.university.Faculty;

@Component
public class FacultyDTOToFacultyConverter implements Converter<FacultyDTO, Faculty> {

    @Override
    public Faculty convert(FacultyDTO facultyDTO) {
        return Faculty.builder()
            .facultyId(facultyDTO.getFacultyId())
            .universityId(facultyDTO.getUniversityId())
            .facultyName(facultyDTO.getFacultyName())
            .build();
    }
}
