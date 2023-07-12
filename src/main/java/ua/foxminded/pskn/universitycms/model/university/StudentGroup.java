package ua.foxminded.pskn.universitycms.model.university;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "student_group")
public class StudentGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupId;

    private int facultyId;

    @Column(name = "name")
    private String groupName;


}
