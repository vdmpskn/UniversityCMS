package ua.foxminded.pskn.universitycms.model.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "professors")
public class Professor {
    @Id
    private Long userId;

    private int professorId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getProfessorId() {
        return professorId;
    }
}
