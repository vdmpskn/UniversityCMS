package ua.foxminded.pskn.universitycms.converter.faculty;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.foxminded.pskn.universitycms.dto.FacultyDTO;
import ua.foxminded.pskn.universitycms.model.university.Faculty;

@Component
public class FacultyToFacultyDTOConverter implements Converter<Faculty, FacultyDTO> {

    @Override
    public FacultyDTO convert(Faculty faculty) {
        return FacultyDTO.builder()
            .facultyId(faculty.getFacultyId())
            .universityId(faculty.getUniversityId())
            .facultyName(faculty.getFacultyName())
            .build();
    }
}
