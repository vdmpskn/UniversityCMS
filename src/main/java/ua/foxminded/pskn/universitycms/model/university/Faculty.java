package ua.foxminded.pskn.universitycms.model.university;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "faculty")
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long facultyId;

    private int universityId;

    @Column(name = "name")
    private String facultyName;

}
