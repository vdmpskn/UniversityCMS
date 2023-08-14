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

    private String facultyName;

    private int universityId;

    public static FacultyDTO fromFaculty(Faculty faculty) {
        return FacultyDTO.builder()
            .facultyName(faculty.getFacultyName())
            .universityId(faculty.getUniversityId())
            .build();
    }

    public Faculty toFaculty() {
        Faculty faculty = new Faculty();
        faculty.setFacultyName(facultyName);
        faculty.setUniversityId(universityId);
        return faculty;
    }

}
