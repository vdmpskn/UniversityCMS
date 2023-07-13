package ua.foxminded.pskn.universitycms.service.university;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.foxminded.pskn.universitycms.model.university.StudentGroup;
import ua.foxminded.pskn.universitycms.repository.university.StudentGroupRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StudentGroupService {
    private final StudentGroupRepository studentGroupRepository;

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
