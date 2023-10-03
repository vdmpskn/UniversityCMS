package ua.foxminded.pskn.persistence.domain.school;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import ua.foxminded.pskn.domain.school.School;
import ua.foxminded.pskn.domain.school.SchoolDao;
import ua.foxminded.pskn.persistence.domain.JpaAbstractDao;

@Component
class JpaSchoolDao extends JpaAbstractDao<SchoolEntity, School> implements SchoolDao {

    private final SchoolPersistenceMapper mapper;
    private final SchoolRepository repository;

    @Autowired
    JpaSchoolDao(
        final SchoolPersistenceMapper mapper,
        final SchoolRepository repository
    ) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public Optional<School> findBySchoolName(final String name) {
        return repository
            .findBySchoolName(name)
            .map(this::toDomain);
    }

    @Override
    protected JpaRepository<SchoolEntity, UUID> getRepository() {
        return repository;
    }

    @Override
    protected SchoolEntity toEntity(final School school) {
        return mapper.toEntity(school);
    }

    @Override
    protected School toDomain(final SchoolEntity schoolEntity) {
        return mapper.toDomain(schoolEntity);
    }
}
