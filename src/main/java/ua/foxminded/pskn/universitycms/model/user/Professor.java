package ua.foxminded.pskn.universitycms.model.user;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "professors")
public class Professor {

    private Long userId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "professor_id")
    private int professorId;
}
