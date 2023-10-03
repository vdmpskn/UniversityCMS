package ua.foxminded.pskn.domain.school;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
class BaseSchoolService implements SchoolService {

    private final SchoolDao schoolDao;

    @Autowired
    BaseSchoolService(final SchoolDao schoolDao) {
        this.schoolDao = schoolDao;
    }

    @Override
    public List<School> findAll() {
        return schoolDao.findAll();
    }

    @Override
    public School save(final School school) {
        return schoolDao.save(school);
    }

    @Override
    public void deleteById(final UUID id) {
        schoolDao.deleteById(id);
    }
}
