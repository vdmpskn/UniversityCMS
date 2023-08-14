package ua.foxminded.pskn.universitycms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.foxminded.pskn.universitycms.model.user.User;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String username;

    private String role;

    private int facultyId;

    public static UserDTO fromUser(User user) {
        return UserDTO.builder()
            .username(user.getUsername())
            .role(user.getRole())
            .facultyId(user.getFacultyId())
            .build();
    }

    public User toUser() {
        return User.builder()
            .username(username)
            .role(role)
            .facultyId(facultyId)
            .build();
    }

}
