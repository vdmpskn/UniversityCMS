package ua.foxminded.pskn.universitycms.converter.university;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.foxminded.pskn.universitycms.dto.UniversityDTO;
import ua.foxminded.pskn.universitycms.model.university.University;

@Component
public class UniversityToUniversityDTOConverter implements Converter<University, UniversityDTO> {

    @Override
    public UniversityDTO convert(University university) {
        return UniversityDTO.builder()
            .universityId(university.getUniversityId())
            .universityName(university.getUniversityName())
            .build();
    }
}
