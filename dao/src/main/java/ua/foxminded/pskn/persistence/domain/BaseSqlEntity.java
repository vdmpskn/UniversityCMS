package ua.foxminded.pskn.persistence.domain;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseSqlEntity implements BaseEntity {

    @Id
    @Column(name = "id")
    protected UUID id;

    @Column(name = "created_time")
    protected long createdTime;

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(final UUID id) {
        this.id = id;
    }

    @Override
    public long getCreatedTime() {
        return createdTime;
    }

    @Override
    public void setCreatedTime(final long createdTime) {
        if (createdTime > 0) {
            this.createdTime = createdTime;
        }
    }
}
