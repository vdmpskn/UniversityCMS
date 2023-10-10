package ua.foxminded.pskn.universitycms.service.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.MockitoAnnotations;

import ua.foxminded.pskn.universitycms.converter.role.RoleConverter;
import ua.foxminded.pskn.universitycms.dto.RoleDTO;
import ua.foxminded.pskn.universitycms.model.user.Role;
import ua.foxminded.pskn.universitycms.repository.user.RoleRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class RoleServiceTest {

    @InjectMocks
    private RoleService roleService;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private RoleConverter roleConverter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetAllRoles() {
        List<Role> mockRoles = Arrays.asList(
            new Role(1, "Role1"),
            new Role(2, "Role2"),
            new Role(3, "Role3")
        );

        when(roleRepository.findAll()).thenReturn(mockRoles);

        when(roleConverter.convertToDTO(any(Role.class)))
            .thenAnswer(invocation -> {
                Role roleEntity = invocation.getArgument(0);
                return new RoleDTO(roleEntity, "RoleName" + roleEntity.getRoleId());
            });

        List<RoleDTO> resultRoleDTOs = roleService.getAllRoles();

        assertThat(resultRoleDTOs)
            .isNotNull()
            .hasSize(mockRoles.size())
            .containsExactly(
                new RoleDTO(mockRoles.get(0), "RoleName1"),
                new RoleDTO(mockRoles.get(1), "RoleName2"),
                new RoleDTO(mockRoles.get(2), "RoleName3")
            );

        verify(roleRepository, times(1)).findAll();

        verify(roleConverter, times(mockRoles.size())).convertToDTO(any(Role.class));
    }

    @Test
    void shouldFindRoleById() {
        int roleId = 1;
        Role mockRole = new Role(roleId, "RoleName");
        RoleDTO mockRoleDTO = new RoleDTO(mockRole, "RoleName");

        when(roleRepository.findById(roleId)).thenReturn(Optional.of(mockRole));
        when(roleConverter.convertToDTO(mockRole)).thenReturn(mockRoleDTO);

        Optional<RoleDTO> resultRoleDTO = roleService.findRoleById(roleId);

        assertThat(resultRoleDTO)
            .isPresent()
            .contains(mockRoleDTO);

        verify(roleRepository, times(1)).findById(roleId);

        verify(roleConverter, times(1)).convertToDTO(mockRole);
    }
}
