package ua.foxminded.pskn.universitycms.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.foxminded.pskn.universitycms.model.user.Student;
import ua.foxminded.pskn.universitycms.repository.user.StudentRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public Optional<Student> getStudentByUserId(Long userId) {
        log.debug("Getting student by userId: {}", userId);
        return studentRepository.getStudentByUserId(userId);
    }

    public void changeMyGroup(Long studentID, int newGroupId) {
        Optional<Student> studentOptional = studentRepository.findById(studentID);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            student.setGroupId(newGroupId);
            studentRepository.save(student);
            log.info("Updated student with ID {} to new group ID: {}", student.getUserId(), newGroupId);
        } else {
            log.error("Student with ID {} not found.", studentID);
        }
    }

    public List<Student> getAllStudents() {
        log.debug("Retrieving all students");
        return studentRepository.findAll();
    }

    public Page<Student> getAllStudents(Pageable pageable) {
        log.debug("Retrieving all students with page number: {} and page size: {}", pageable.getPageNumber(), pageable.getPageSize());
        return studentRepository.findAll(pageable);
    }

}
