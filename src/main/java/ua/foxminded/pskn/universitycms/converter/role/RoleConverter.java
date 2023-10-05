package ua.foxminded.pskn.universitycms.converter.role;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ua.foxminded.pskn.universitycms.dto.RoleDTO;
import ua.foxminded.pskn.universitycms.model.user.Role;


@Component
@RequiredArgsConstructor
public class RoleConverter {

    private final ModelMapper modelMapper;

    public RoleDTO convertToDTO(Role role) {
        return modelMapper.map(role, RoleDTO.class);
    }

    public Role convertToEntity(RoleDTO roleDTO) {
        return modelMapper.map(roleDTO, Role.class);
    }
}
