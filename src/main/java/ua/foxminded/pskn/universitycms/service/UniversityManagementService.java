package ua.foxminded.pskn.universitycms.service;

import org.springframework.stereotype.Service;
import ua.foxminded.pskn.universitycms.model.university.Faculty;
import ua.foxminded.pskn.universitycms.model.university.StudentGroup;
import ua.foxminded.pskn.universitycms.model.university.University;
import ua.foxminded.pskn.universitycms.repository.university.FacultyRepository;
import ua.foxminded.pskn.universitycms.repository.university.StudentGroupRepository;
import ua.foxminded.pskn.universitycms.repository.university.UniversityRepository;

import java.util.List;

@Service
public class UniversityManagementService {

    private final UniversityRepository universityRepository;

    private final FacultyRepository facultyRepository;

    private final StudentGroupRepository studentGroupRepository;

    public UniversityManagementService(UniversityRepository universityRepository, FacultyRepository facultyRepository, StudentGroupRepository studentGroupRepository) {
        this.universityRepository = universityRepository;
        this.facultyRepository = facultyRepository;
        this.studentGroupRepository = studentGroupRepository;
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

    public StudentGroup saveStudentGroup(StudentGroup studentGroup) {
        return studentGroupRepository.save(studentGroup);
    }

    public StudentGroup getStudentGroupById(Long id) {
        return studentGroupRepository.findById(id).orElse(null);
    }

    public List<StudentGroup> getAllStudentGroups() {
        return studentGroupRepository.findAll();
    }

    public void deleteStudentGroup(Long id) {
        studentGroupRepository.deleteById(id);
    }

}
