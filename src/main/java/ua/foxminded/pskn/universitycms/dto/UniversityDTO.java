package ua.foxminded.pskn.universitycms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.foxminded.pskn.universitycms.model.university.University;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UniversityDTO {

    private Long universityId;
    private String universityName;

    public static UniversityDTO fromUniversity(University university) {
        return UniversityDTO.builder()
            .universityId(university.getUniversityId())
            .universityName(university.getUniversityName())
            .build();
    }

    public University toUniversity() {
        University university = new University();
        university.setUniversityId(universityId);
        university.setUniversityName(universityName);
        return university;
    }
}

