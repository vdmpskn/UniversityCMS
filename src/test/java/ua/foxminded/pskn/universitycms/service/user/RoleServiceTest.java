package ua.foxminded.pskn.universitycms.service.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.foxminded.pskn.universitycms.model.user.Role;
import ua.foxminded.pskn.universitycms.repository.user.RoleRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RoleServiceTest {

    @InjectMocks
    private RoleService roleService;

    @Mock
    private RoleRepository roleRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        List<Role> roles = Arrays.asList(
            new Role(1, "ADMIN"),
            new Role(2, "STUDENT")
        );
        when(roleRepository.findAll()).thenReturn(roles);
    }

    @Test
    void shouldGetAllRoles() {
        List<Role> roles = roleService.getAllRoles();

        assertNotNull(roles);
        assertEquals(2, roles.size());
    }
}
