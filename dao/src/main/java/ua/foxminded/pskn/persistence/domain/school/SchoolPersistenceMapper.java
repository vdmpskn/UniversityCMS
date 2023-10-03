package ua.foxminded.pskn.persistence.domain.school;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ua.foxminded.pskn.domain.school.School;

@Mapper
interface SchoolPersistenceMapper {

    @Mapping(source = "id", target = "schoolId")
    School toDomain(final SchoolEntity schoolEntity);

    @Mapping(source = "schoolId", target = "id")
    SchoolEntity toEntity(final School school);

}
