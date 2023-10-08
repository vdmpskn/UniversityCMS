package ua.foxminded.pskn.universitycms.converter.university;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ua.foxminded.pskn.universitycms.dto.UniversityDTO;
import ua.foxminded.pskn.universitycms.model.university.University;

@Component
@RequiredArgsConstructor
public class UniversityConverter {

    private final ModelMapper modelMapper;

    public UniversityDTO convertToDTO(University university){return modelMapper.map(university, UniversityDTO.class);}

    public University convertToEntity(UniversityDTO universityDTO){return modelMapper.map(universityDTO, University.class);}

}
