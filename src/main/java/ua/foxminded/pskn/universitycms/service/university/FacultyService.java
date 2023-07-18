package ua.foxminded.pskn.universitycms.service.university;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.foxminded.pskn.universitycms.model.university.Faculty;
import ua.foxminded.pskn.universitycms.repository.university.FacultyRepository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public Faculty saveFaculty(Faculty faculty) {
        log.info("Saving faculty: {}", faculty);
        return facultyRepository.save(faculty);
    }

    public Faculty getFacultyById(Long id) {
        return facultyRepository.findById(id).orElse(null);
    }

    public List<Faculty> getAllFaculties() {
        log.debug("Retrieving all schedules");
        return facultyRepository.findAll();
    }

    public Page<Faculty> getAllFaculties(Pageable pageable){
        log.debug("Retrieving all faculties");
        return facultyRepository.findAll(pageable);
    }

    public void deleteFaculty(Long id) {
        log.info("Deleting faculty with ID: {}", id);
        facultyRepository.deleteById(id);
    }
}
