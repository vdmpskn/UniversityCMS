package ua.foxminded.pskn.universitycms.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.foxminded.pskn.universitycms.model.user.Student;
import ua.foxminded.pskn.universitycms.repository.university.StudentGroupRepository;
import ua.foxminded.pskn.universitycms.repository.user.StudentRepository;

import java.util.Scanner;

@Slf4j
@RequiredArgsConstructor
@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final StudentGroupRepository studentGroupRepository;
    Scanner scanner = new Scanner(System.in);

    public Student getStudentByUserId(Long userId){
        log.debug("Getting student by userId: {}", userId);
        return studentRepository.getStudentByUserId(userId);
    }

    public void changeMyGroup(Student student){
        studentGroupRepository.findAll();
        log.info("Your groupID is: {}", student.getGroupId());
        log.info("Write your new groupID: ");
        int changedGroupId = scanner.nextInt();
        student.setGroupId(changedGroupId);
        log.info("Saving student with updated groupID: {}", student);
        studentRepository.save(student);
    }
}
