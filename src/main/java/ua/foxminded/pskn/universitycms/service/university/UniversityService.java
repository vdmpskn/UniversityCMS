package ua.foxminded.pskn.universitycms.service.university;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.foxminded.pskn.universitycms.model.university.University;
import ua.foxminded.pskn.universitycms.repository.university.UniversityRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UniversityService {

    private final UniversityRepository universityRepository;

    public University saveUniversity(University university) {
        log.info("Saving university: {}", university);
        return universityRepository.save(university);
    }

    public University saveUniversityByName(String universityName) {
        University newUniversity = new University();
        newUniversity.setUniversityName(universityName);
        log.info("Saving university: {}", universityName);
        return universityRepository.save(newUniversity);
    }

    public boolean isUniversityExistByUniversityId(int universityId){

        return universityRepository.existsByUniversityId(universityId);

    }

    public University findUniversityByName(String name){
        return universityRepository.findByUniversityName(name);
    }

    @Transactional
    public void updateUniversityName(String universityName, Long id){

        log.info("Update university: {}", universityName);
        universityRepository.updateUniversityName(id, universityName);
    }

    @Transactional
    public boolean deleteUniversityByName(String name) {
        University university = universityRepository.findByUniversityName(name);
        if (university != null) {
            universityRepository.delete(university);
            return true;
        }
        return false;
    }


    public University getUniversityById(Long id) {
        log.debug("Retrieving university by ID: {}", id);
        return universityRepository.findById(id).orElse(null);
    }

    public List<University> getAllUniversities() {
        log.debug("Retrieving all universities");
        return universityRepository.findAll();
    }

    public Page<University> getAllUniversities(Pageable pageable){
        log.debug("Retrieving all universities with page number: {} and page size: {}", pageable.getPageNumber(), pageable.getPageSize());
        return universityRepository.findAll(pageable);
    }

    public void deleteUniversity(Long id) {
        log.info("Deleting university with ID: {}", id);
        universityRepository.deleteById(id);
    }

}
