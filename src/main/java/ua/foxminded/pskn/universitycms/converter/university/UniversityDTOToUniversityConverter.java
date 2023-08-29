package ua.foxminded.pskn.universitycms.converter.university;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.foxminded.pskn.universitycms.dto.UniversityDTO;
import ua.foxminded.pskn.universitycms.model.university.University;

@Component
public class UniversityDTOToUniversityConverter implements Converter<UniversityDTO, University> {

    @Override
    public University convert(UniversityDTO dto) {
        University university = new University();
        university.setUniversityId(dto.getUniversityId());
        university.setUniversityName(dto.getUniversityName());
        return university;
    }
}
