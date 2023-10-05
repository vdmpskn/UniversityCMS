package ua.foxminded.pskn.universitycms.converter.user;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ua.foxminded.pskn.universitycms.converter.role.RoleConverter;
import ua.foxminded.pskn.universitycms.dto.UserDTO;
import ua.foxminded.pskn.universitycms.model.user.User;

@Component
@RequiredArgsConstructor
public class UserConverter {

    private final ModelMapper modelMapper;

    private final RoleConverter roleConverter;

    public UserDTO convertToDTO(User user) {

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.setRoleDTO(roleConverter.convertToDTO(user.getRole()));
        return userDTO;
    }

    public User convertToEntity(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        user.setRole(roleConverter.convertToEntity(userDTO.getRoleDTO()));
        return user;
    }
}
