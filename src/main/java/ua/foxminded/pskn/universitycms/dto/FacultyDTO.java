package ua.foxminded.pskn.universitycms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.foxminded.pskn.universitycms.model.university.Faculty;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FacultyDTO {

    private Long facultyId;

    private String facultyName;

    private int universityId;

}
