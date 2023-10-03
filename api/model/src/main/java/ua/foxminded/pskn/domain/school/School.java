package ua.foxminded.pskn.domain.school;

import java.util.UUID;

import ua.foxminded.pskn.domain.Domain;

public record School(
    UUID schoolId,
    String schoolName,
    long createdTime
) implements Domain {

    public School withSchoolId(final UUID schoolId) {
        return new School(
            schoolId,
            this.schoolName,
            this.createdTime
        );
    }

    public School withSchoolName(final String schoolName) {
        return new School(
            this.schoolId,
            schoolName,
            this.createdTime
        );
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private UUID schoolId;
        private String schoolName;
        private long createdTime;

        public Builder schoolId(final UUID schoolId) {
            this.schoolId = schoolId;
            return this;
        }

        public Builder schoolName(final String schoolName) {
            this.schoolName = schoolName;
            return this;
        }

        public Builder createdTime(final long createdTime) {
            this.createdTime = createdTime;
            return this;
        }

        public School build() {
            return new School(
                schoolId,
                schoolName,
                createdTime
            );
        }
    }
}
