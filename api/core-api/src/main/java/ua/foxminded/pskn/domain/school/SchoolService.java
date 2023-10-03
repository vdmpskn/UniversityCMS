package ua.foxminded.pskn.domain.school;

import java.util.List;
import java.util.UUID;

public interface SchoolService {
    List<School> findAll();

    School save(final School school);

    void deleteById(final UUID id);
}
