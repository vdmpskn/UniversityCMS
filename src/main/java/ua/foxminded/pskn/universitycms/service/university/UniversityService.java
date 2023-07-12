package ua.foxminded.pskn.universitycms.service.university;

import org.springframework.stereotype.Service;
import ua.foxminded.pskn.universitycms.model.university.University;
import ua.foxminded.pskn.universitycms.repository.university.UniversityRepository;

import java.util.List;

@Service
public class UniversityService {

    private final UniversityRepository universityRepository;


    public UniversityService(UniversityRepository universityRepository) {
        this.universityRepository = universityRepository;

    }

    public University saveUniversity(University university) {
        return universityRepository.save(university);
    }

    public University getUniversityById(Long id) {
        return universityRepository.findById(id).orElse(null);
    }

    public List<University> getAllUniversities() {
        return universityRepository.findAll();
    }

    public void deleteUniversity(Long id) {
        universityRepository.deleteById(id);
    }




}
