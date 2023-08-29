package ua.foxminded.pskn.universitycms.service.university;

import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.foxminded.pskn.universitycms.converter.faculty.FacultyDTOToFacultyConverter;
import ua.foxminded.pskn.universitycms.converter.faculty.FacultyToFacultyDTOConverter;
import ua.foxminded.pskn.universitycms.customexception.FacultyEditException;
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

    private final FacultyToFacultyDTOConverter toFacultyDTOConverter;

    public FacultyDTO saveFaculty(FacultyDTO facultyDTO) {
        if (StringUtils.isNotBlank(facultyDTO.getFacultyName())) {
            try {
                Faculty faculty = toFacultyConverter.convert(facultyDTO);
                faculty = facultyRepository.save(faculty);
                return toFacultyDTOConverter.convert(faculty);
            } catch (DataAccessException e) {
                throw new FacultyEditException("Faculty with name " + facultyDTO.getFacultyName() + " already exists.");
            }
        } else {
            throw new FacultyEditException("Faculty name cannot be blank.");
        }
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
            log.info("Update faculty: {}", facultyDTO.getFacultyName());
            facultyRepository.updateFacultyNameById(facultyDTO.getFacultyId(), facultyDTO.getFacultyName());
            if (!facultyRepository.existsById(facultyDTO.getFacultyId())) {
                throw new FacultyNotFoundException("Faculty not found.");
            }
        } else {
            throw new IllegalArgumentException("Faculty name cannot be blank.");
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
        if (!facultyRepository.existsById(facultyId)) {
            throw new FacultyNotFoundException("Faculty with ID " + facultyId + " not found");
        }
        facultyRepository.deleteById(facultyId);
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
