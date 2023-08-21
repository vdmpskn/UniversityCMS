package ua.foxminded.pskn.universitycms.service.university;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.foxminded.pskn.universitycms.converter.university.UniversityDTOToUniversityConverter;
import ua.foxminded.pskn.universitycms.customexception.UniversityNotFoundException;
import ua.foxminded.pskn.universitycms.dto.UniversityDTO;
import ua.foxminded.pskn.universitycms.model.university.University;
import ua.foxminded.pskn.universitycms.repository.university.UniversityRepository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UniversityService {

    private final UniversityRepository universityRepository;

    private final UniversityDTOToUniversityConverter toUniversityConverter;

    public University saveUniversity(UniversityDTO universityDTO) {
        try{
            University university = toUniversityConverter.convert(universityDTO);
            log.info("Saving university: {}", university);
            return universityRepository.save(university);
        } catch (UniversityNotFoundException ex){
            ex.printStackTrace();
        }
        return null;
    }

    public University saveUniversityByName(String universityName) {
        University newUniversity = new University();
        newUniversity.setUniversityName(universityName);
        log.info("Saving university: {}", universityName);
        return universityRepository.save(newUniversity);
    }

    public boolean isUniversityExistByUniversityId(int universityId) {
        return universityRepository.existsByUniversityId(universityId);
    }

    public University findUniversityByName(String name) {
        return universityRepository.findByUniversityName(name);
    }

    @Transactional
    public void updateUniversityName(UniversityDTO universityDTO) {
        log.info("Update university: {}", universityDTO.getUniversityName());
        try {
            universityRepository.updateUniversityName(universityDTO.getUniversityId(), universityDTO.getUniversityName());
        } catch (UniversityNotFoundException ex) {
            throw new UniversityNotFoundException("University not found with ID " + universityDTO.getUniversityId());
        }
    }

    @Transactional
    public boolean deleteUniversityByName(UniversityDTO universityDTO) {
        University university = toUniversityConverter.convert(universityDTO);
        log.info("Deleting university with ID: {}", university.getUniversityId());
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

    public Page<University> getAllUniversities(Pageable pageable) {
        log.debug("Retrieving all universities with page number: {} and page size: {}", pageable.getPageNumber(), pageable.getPageSize());
        return universityRepository.findAll(pageable);
    }

    @Transactional
    public void deleteUniversity(Long id) {
        log.info("Deleting university with ID: {}", id);
        try {
            universityRepository.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException("Unable to delete university with ID " + id + ". Data integrity violation.");
        }
    }

}
