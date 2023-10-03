package ua.foxminded.pskn.persistence.domain.school;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface SchoolRepository extends JpaRepository<SchoolEntity, UUID> {

    Optional<SchoolEntity> findBySchoolName(final String schoolName);

}
