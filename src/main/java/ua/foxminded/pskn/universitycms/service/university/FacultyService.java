package ua.foxminded.pskn.universitycms.service.university;

import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.foxminded.pskn.universitycms.converter.faculty.FacultyDTOToFacultyConverter;
import ua.foxminded.pskn.universitycms.customexception.FacultyNotFoundException;
import ua.foxminded.pskn.universitycms.dto.FacultyDTO;
import ua.foxminded.pskn.universitycms.model.university.Faculty;
import ua.foxminded.pskn.universitycms.repository.university.FacultyRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    private final FacultyDTOToFacultyConverter toFacultyConverter;

    public Faculty saveFaculty(FacultyDTO facultyDTO) {
        if (StringUtils.isNotBlank(facultyDTO.getFacultyName())) {
            try {
                log.info("Saving faculty: {}", facultyDTO);
                return facultyRepository.save(toFacultyConverter.convert(facultyDTO));
            } catch (FacultyNotFoundException ex) {
                log.error("Faculty not found exception! Because: " + ex.getMessage());
            }
        }
        return null;
    }

    public Faculty saveFacultyByName(String facultyName, int universityId) {
        Faculty newFaculty = new Faculty();
        newFaculty.setFacultyName(facultyName);
        newFaculty.setUniversityId(universityId);
        log.info("Saving faculty: {}", newFaculty);
        return facultyRepository.save(newFaculty);
    }

    @Transactional
    public void updateFacultyName(FacultyDTO facultyDTO) {
        if (StringUtils.isNotBlank(facultyDTO.getFacultyName())) {
            try {
                log.info("Update faculty: {}", facultyDTO.getFacultyName());
                facultyRepository.updateFacultyNameById(facultyDTO.getFacultyId(), facultyDTO.getFacultyName());
            } catch (FacultyNotFoundException ex) {
                log.error("Faculty not found exception! Because: " + ex.getMessage());
            }
        }
    }

    public boolean hasFacultiesWithUniversityId(Long universityId) {
        return facultyRepository.existsById(universityId);
    }

    @Transactional
    public boolean deleteFacultyByName(FacultyDTO facultyDTO) {
        log.info("Delete faculty: {}", facultyDTO.getFacultyName());
        Faculty faculty = facultyRepository.findByFacultyName(facultyDTO.getFacultyName()).orElse(null);
        if (faculty != null) {
            facultyRepository.delete(faculty);
            return true;
        }
        return false;
    }

    @Transactional
    public void deleteFacultyById(Long facultyId) {
        log.info("Delete faculty with ID: {}", facultyId);
        Optional<Faculty> facultyOptional = facultyRepository.findById(facultyId);
        if (facultyOptional.isPresent()) {
            facultyRepository.delete(facultyOptional.get());
        } else {
            throw new FacultyNotFoundException("Faculty with ID " + facultyId + " not found");
        }
    }

    public Faculty getFacultyById(Long id) {
        return facultyRepository.findById(id).orElse(null);
    }

    public List<Faculty> getAllFaculties() {
        log.debug("Retrieving all schedules");
        return facultyRepository.findAll();
    }

    public Page<Faculty> getAllFaculties(Pageable pageable){
        log.debug("Retrieving all faculties with page number: {} and page size: {}", pageable.getPageNumber(), pageable.getPageSize());
        return facultyRepository.findAll(pageable);
    }

    public void deleteFaculty(Long id) {
        log.info("Deleting faculty with ID: {}", id);
        facultyRepository.deleteById(id);
    }
}
