package ua.foxminded.pskn.universitycms.converter.professor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ua.foxminded.pskn.universitycms.dto.ProfessorDTO;
import ua.foxminded.pskn.universitycms.model.user.Professor;

@Component
@RequiredArgsConstructor
public class ProfessorConverter {
    private final ModelMapper modelMapper;

    public ProfessorDTO convertToDTO(Professor professor){return modelMapper.map(professor, ProfessorDTO.class);}

    public Professor convertToEntity(ProfessorDTO professorDTO){return modelMapper.map(professorDTO, Professor.class);}
}
