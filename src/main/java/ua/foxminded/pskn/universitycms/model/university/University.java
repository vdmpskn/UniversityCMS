package ua.foxminded.pskn.universitycms.model.university;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "university")
public class University {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long universityId;

    @Column(name = "name")
    private String universityName;
}
