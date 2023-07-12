package ua.foxminded.pskn.universitycms.model.university;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "university")
public class University {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long universityId;

    @Column(name = "name")
    private String universityName;
}
