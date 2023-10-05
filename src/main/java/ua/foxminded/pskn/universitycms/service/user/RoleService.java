package ua.foxminded.pskn.universitycms.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import ua.foxminded.pskn.universitycms.converter.role.RoleConverter;
import ua.foxminded.pskn.universitycms.dto.RoleDTO;
import ua.foxminded.pskn.universitycms.model.user.Role;
import ua.foxminded.pskn.universitycms.repository.user.RoleRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    private final RoleConverter roleConverter;


    public List<RoleDTO> getAllRoles(){
        return roleRepository.findAll()
            .stream()
            .map(roleConverter::convertToDTO)
            .collect(Collectors.toList());
    }

    public Optional<RoleDTO> findRoleById(int id){
         Optional<Role> roleOptional = roleRepository.findById(id);
         return roleOptional.map(roleConverter::convertToDTO);
    }

}
