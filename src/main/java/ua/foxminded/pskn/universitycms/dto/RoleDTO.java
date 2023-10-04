package ua.foxminded.pskn.universitycms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import ua.foxminded.pskn.universitycms.model.user.Role;

@Data
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {

    private Role role;

    private String roleName;
}
