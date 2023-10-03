package ua.foxminded.pskn.persistence.domain;

import java.util.UUID;

public interface BaseEntity {

    UUID getId();

    void setId(final UUID id);

    long getCreatedTime();

    void setCreatedTime(final long createdTime);
}
