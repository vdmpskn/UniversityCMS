package ua.foxminded.pskn.domain.school;

import java.util.Optional;

import ua.foxminded.pskn.domain.Dao;

public interface SchoolDao extends Dao<School> {

    Optional<School> findBySchoolName(final String name);

}
