package ua.foxminded.pskn.universitycms.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.foxminded.pskn.universitycms.model.user.Role;
import ua.foxminded.pskn.universitycms.repository.user.RoleRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RoleService {

    private final RoleRepository roleRepository;


    public List<Role> getAllRoles(){
        return roleRepository.findAll();
    }

}
