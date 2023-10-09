package ua.foxminded.pskn.universitycms.service.university;

import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ua.foxminded.pskn.universitycms.converter.studentgroup.StudentGroupConverter;
import ua.foxminded.pskn.universitycms.customexception.StudentGroupEditException;
import ua.foxminded.pskn.universitycms.customexception.StudentGroupNotFoundException;
import ua.foxminded.pskn.universitycms.dto.StudentGroupDTO;
import ua.foxminded.pskn.universitycms.model.university.StudentGroup;
import ua.foxminded.pskn.universitycms.repository.university.StudentGroupRepository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class StudentGroupService {
    private final StudentGroupRepository studentGroupRepository;

    private final StudentGroupConverter studentGroupConverter;

    public StudentGroupDTO saveStudentGroup(StudentGroupDTO studentGroupDTO) {
        log.info("Saving university: {}", studentGroupDTO.getGroupName());
        if (StringUtils.isNotBlank(studentGroupDTO.getGroupName())) {
            try {
                studentGroupRepository.save(studentGroupConverter.convertToEntity(studentGroupDTO));
                return studentGroupDTO;
            } catch (DataAccessException ex) {
                throw new StudentGroupEditException("Student Group with name " + studentGroupDTO.getGroupName() + " already exists.");
            }
        } else {
            throw new IllegalArgumentException("Student Group name cannot be blank.");
        }
    }

    @Transactional
    public void updateStudentGroupName(StudentGroupDTO studentGroupDTO) {
        if (StringUtils.isNotBlank(studentGroupDTO.getGroupName())) {
            log.info("Update student group: {}", studentGroupDTO.getGroupName());
            studentGroupRepository.updateStudentGroupName(studentGroupDTO.getGroupId(), studentGroupDTO.getGroupName());
            if (!studentGroupRepository.existsById(studentGroupDTO.getGroupId())) {
                throw new StudentGroupNotFoundException("Student Group not found with ID: " + studentGroupDTO.getGroupId());
            }
        } else {
            throw new IllegalArgumentException("Student Group name cannot be blank.");
        }
    }

    public StudentGroup getStudentGroupById(Long id) {
        log.debug("Retrieving student group by ID: {}", id);
        return studentGroupRepository.findById(id).orElse(null);
    }

    public List<StudentGroup> getAllStudentGroups() {
        log.debug("Retrieving all student groups");
        return studentGroupRepository.findAll();
    }

    public Page<StudentGroupDTO> getAllStudentGroups(Pageable pageable) {
        log.debug("Retrieving all students group with page number: {} and page size: {}", pageable.getPageNumber(), pageable.getPageSize());
        Page<StudentGroup> studentGroupPage = studentGroupRepository.findAll(pageable);
        return studentGroupPage.map(studentGroupConverter::convertToDTO);
    }

    public void deleteStudentGroup(Long id) {
        log.info("Deleting student group with ID: {}", id);
        studentGroupRepository.deleteById(id);
    }
}
