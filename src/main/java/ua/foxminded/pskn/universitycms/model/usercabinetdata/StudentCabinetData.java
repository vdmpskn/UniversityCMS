package ua.foxminded.pskn.universitycms.model.usercabinetdata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.foxminded.pskn.universitycms.model.university.StudentGroup;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentCabinetData {
    private Long userID;
    private String username;
    private Long studentId;
    private String studentGroup;
    private List<StudentGroup> availableGroups;
}
