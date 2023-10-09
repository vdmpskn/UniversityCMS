package ua.foxminded.pskn.universitycms.service.university;

import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ua.foxminded.pskn.universitycms.converter.university.UniversityConverter;
import ua.foxminded.pskn.universitycms.converter.university.UniversityDTOToUniversityConverter;
import ua.foxminded.pskn.universitycms.converter.university.UniversityToUniversityDTOConverter;
import ua.foxminded.pskn.universitycms.customexception.UniversityEditException;
import ua.foxminded.pskn.universitycms.customexception.UniversityNotFoundException;
import ua.foxminded.pskn.universitycms.dto.UniversityDTO;
import ua.foxminded.pskn.universitycms.model.university.University;
import ua.foxminded.pskn.universitycms.repository.university.UniversityRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class UniversityService {

    private final UniversityRepository universityRepository;

    private final UniversityConverter universityConverter;

    public UniversityDTO saveUniversity(UniversityDTO universityDTO) {
        log.info("Saving university: {}", universityDTO.getUniversityName());
        if (StringUtils.isNotBlank(universityDTO.getUniversityName())) {
            try {
                universityRepository.save(universityConverter.convertToEntity(universityDTO));
                return universityDTO;
            }
            catch (DataAccessException ex) {
                throw new UniversityEditException("University with name " + universityDTO.getUniversityName() + " already exists.");
            }
        }
        else {
            throw new IllegalArgumentException("University name cannot be blank.");
        }
    }

    public University saveUniversityByName(String universityName) {
        University newUniversity = new University();
        newUniversity.setUniversityName(universityName);
        log.info("Saving university: {}", universityName);
        return universityRepository.save(newUniversity);
    }

    public boolean isUniversityExistByUniversityId(Long universityId) {
        return universityRepository.existsByUniversityId(universityId);
    }

    public Optional<University> findUniversityByName(String name) {
        return universityRepository.findByUniversityName(name);
    }

    @Transactional
    public void updateUniversityName(UniversityDTO universityDTO) {
        if (StringUtils.isNotBlank(universityDTO.getUniversityName())) {
            log.info("Update university: {}", universityDTO.getUniversityName());
            universityRepository.updateUniversityName(universityDTO.getUniversityId(), universityDTO.getUniversityName());
            if (!universityRepository.existsById(universityDTO.getUniversityId())) {
                throw new UniversityNotFoundException("University not found with ID: " + universityDTO.getUniversityId());
            }
        }
        else {
            throw new IllegalArgumentException("University name cannot be blank.");
        }
    }

    @Transactional
    public boolean deleteUniversityByName(UniversityDTO universityDTO) {
        University university = universityConverter.convertToEntity(universityDTO);

        if (university != null) {
            log.info("Deleting university with ID: {}", university.getUniversityId());
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

    public Page<UniversityDTO> getAllUniversities(Pageable pageable) {
        log.debug
            (
                "Retrieving all universities with page number: {} and page size: {}",
                pageable.getPageNumber(),
                pageable.getPageSize()
            );
        Page<University> universityPage = universityRepository.findAll(pageable);

        return universityPage.map(universityConverter::convertToDTO);
    }

    @Transactional
    public void deleteUniversity(Long id) {
        log.info("Deleting university with ID: {}", id);
        if (!universityRepository.existsById(id)) {
            throw new UniversityNotFoundException("University not found with ID: " + id);
        }
        try {
            universityRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException ex) {
            log.error("Unable to delete university with ID " + id + ". Data integrity violation.");
            throw ex;
        }
    }
}
