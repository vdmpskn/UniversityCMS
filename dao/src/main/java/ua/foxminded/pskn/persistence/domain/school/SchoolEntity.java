package ua.foxminded.pskn.persistence.domain.school;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import ua.foxminded.pskn.persistence.domain.BaseSqlEntity;

@Entity
@Table(name = "school")
final class SchoolEntity extends BaseSqlEntity {

    @Column(name = "school_name")
    private String schoolName;

    public SchoolEntity() {
        super();
    }

    public void setSchoolName(final String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolName() {
        return schoolName;
    }
}
