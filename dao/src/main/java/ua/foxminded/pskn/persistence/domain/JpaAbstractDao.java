package ua.foxminded.pskn.persistence.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.uuid.Generators;

import ua.foxminded.pskn.domain.Dao;
import ua.foxminded.pskn.domain.Domain;

public abstract class JpaAbstractDao<E extends BaseEntity, D extends Domain> implements Dao<D> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JpaAbstractDao.class);

    protected abstract JpaRepository<E, UUID> getRepository();

    protected abstract E toEntity(D d);

    protected abstract D toDomain(E e);

    @Override
    @Transactional
    public D save(final D domain) {
        final E entity = toEntity(domain);
        LOGGER.debug("Saving entity: [{}]", entity);
        if (entity.getId() == null) {
            entity.setId(Generators.timeBasedEpochGenerator().generate());
            entity.setCreatedTime(System.currentTimeMillis());
        }
        return toDomain(getRepository().save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<D> findAll() {
        return getRepository()
            .findAll()
            .stream()
            .map(this::toDomain)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<D> findById(final UUID id) {
        return getRepository()
            .findById(id)
            .map(this::toDomain);
    }

    @Override
    @Transactional
    public void deleteById(final UUID id) {
        LOGGER.debug("Delete by id [{}]", id);
        getRepository().deleteById(id);
    }
}
