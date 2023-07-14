package ua.foxminded.pskn.universitycms.service.university;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.foxminded.pskn.universitycms.model.university.StudentGroup;
import ua.foxminded.pskn.universitycms.repository.university.StudentGroupRepository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class StudentGroupService {
    private final StudentGroupRepository studentGroupRepository;

    public StudentGroup saveStudentGroup(StudentGroup studentGroup) {
        log.info("Saving student group: {}", studentGroup);
        return studentGroupRepository.save(studentGroup);
    }

    public StudentGroup getStudentGroupById(Long id) {
        log.debug("Retrieving student group by ID: {}", id);
        return studentGroupRepository.findById(id).orElse(null);
    }

    public List<StudentGroup> getAllStudentGroups() {
        log.debug("Retrieving all student groups");
        return studentGroupRepository.findAll();
    }

    public void deleteStudentGroup(Long id) {
        log.info("Deleting student group with ID: {}", id);
        studentGroupRepository.deleteById(id);
    }
}
