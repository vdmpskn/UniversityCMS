package ua.foxminded.pskn.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface Dao<T> {

    List<T> findAll();

    Optional<T> findById(final UUID id);

    T save(final T t);

    void deleteById(final UUID id);

}
