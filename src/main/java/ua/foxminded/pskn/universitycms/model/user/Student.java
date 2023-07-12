package ua.foxminded.pskn.universitycms.model.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "students")
public class Student {
    @Id
    private Long userId;

    private int groupId;

}
