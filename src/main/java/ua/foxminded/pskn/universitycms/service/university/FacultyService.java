package ua.foxminded.pskn.universitycms.service.university;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.foxminded.pskn.universitycms.model.university.Faculty;
import ua.foxminded.pskn.universitycms.repository.university.FacultyRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public Faculty saveFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty getFacultyById(Long id) {
        return facultyRepository.findById(id).orElse(null);
    }

    public List<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }

    public void deleteFaculty(Long id) {
        facultyRepository.deleteById(id);
    }
}
