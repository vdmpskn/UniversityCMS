package ua.foxminded.pskn.universitycms.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import ua.foxminded.pskn.universitycms.converter.student.StudentConverter;
import ua.foxminded.pskn.universitycms.customexception.ProfessorNotFoundException;
import ua.foxminded.pskn.universitycms.customexception.StudentGroupNotFoundException;
import ua.foxminded.pskn.universitycms.customexception.StudentNotFoundException;
import ua.foxminded.pskn.universitycms.dto.StudentDTO;
import ua.foxminded.pskn.universitycms.model.university.StudentGroup;
import ua.foxminded.pskn.universitycms.model.user.Student;
import ua.foxminded.pskn.universitycms.repository.university.StudentGroupRepository;
import ua.foxminded.pskn.universitycms.repository.user.StudentRepository;


import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class StudentService {
    private final StudentRepository studentRepository;

    private final StudentGroupRepository studentGroupRepository;

    private final StudentConverter studentConverter;

    public Optional<StudentDTO> getStudentByUserId(Long userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("Wrong value of 'userId'");
        }

        log.debug("Getting student by userId: {}", userId);

        return Optional.ofNullable(studentRepository.getStudentByUserId(userId))
            .map(student -> student.map(studentConverter::convertToDTO))
            .orElseThrow(() -> new StudentNotFoundException("Student not found"));
    }



    public void changeMyGroup(Long studentID, Long newGroupId) {
        Optional<StudentGroup> groupOptional = studentGroupRepository.findById(newGroupId);
        if (groupOptional.isEmpty()) {
            log.error("Group with ID {} not found.", newGroupId);
            throw new StudentGroupNotFoundException(String.format("Group with ID %d not found.", newGroupId));
        }
        Optional<Student> studentOptional = studentRepository.findById(studentID);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            student.setGroupId(Math.toIntExact(newGroupId));
            studentRepository.save(student);
            log.info("Updated student with ID {} to new group ID: {}", student.getUserId(), newGroupId);
        } else {
            log.error("Student with ID {} not found.", studentID);
            throw new StudentNotFoundException(String.format("Student with ID %d not found.", studentID));

        }
    }

    public List<Student> getAllStudents() {
        log.debug("Retrieving all students");
        return studentRepository.findAll();
    }

    public Page<StudentDTO> getAllStudents(Pageable pageable) {
        log.debug("Retrieving all students with page number: {} and page size: {}",
            pageable.getPageNumber(),
            pageable.getPageSize());
        Page<Student> studentPage = studentRepository.findAll(pageable);
        return studentPage.map(studentConverter::convertToDTO);
    }

}
