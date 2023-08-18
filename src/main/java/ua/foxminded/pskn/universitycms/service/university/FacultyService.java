package ua.foxminded.pskn.universitycms.service.university;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.foxminded.pskn.universitycms.dto.FacultyDTO;
import ua.foxminded.pskn.universitycms.model.university.Faculty;
import ua.foxminded.pskn.universitycms.model.university.University;
import ua.foxminded.pskn.universitycms.repository.university.FacultyRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public Faculty saveFaculty(Faculty faculty) {
        log.info("Saving faculty: {}", faculty);
        return facultyRepository.save(faculty);
    }

    public Faculty saveFacultyByName(String facultyName, int universityId) {
        Faculty newFaculty = new Faculty();
        newFaculty.setFacultyName(facultyName);
        newFaculty.setUniversityId(universityId);
        log.info("Saving faculty: {}", newFaculty);
        return facultyRepository.save(newFaculty);
    }

    @Transactional
    public void updateFacultyName(FacultyDTO facultyDTO){

        log.info("Update faculty: {}", facultyDTO.getFacultyName());
        facultyRepository.updateFacultyNameById(facultyDTO.getFacultyId(), facultyDTO.getFacultyName());
    }

    public boolean hasFacultiesWithUniversityId(Long universityId) {
        List<Faculty> faculties = facultyRepository.findByUniversityId(universityId);
        return !faculties.isEmpty();
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
    public boolean deleteFacultyById(Long facultyId) {
        log.info("Delete faculty with ID: {}", facultyId);

        Optional<Faculty> facultyOptional = facultyRepository.findById(facultyId);
        if (facultyOptional.isPresent()) {
            facultyRepository.delete(facultyOptional.get());
            return true;
        }
        return false;
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
