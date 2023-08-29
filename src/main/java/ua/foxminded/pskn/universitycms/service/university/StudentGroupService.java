package ua.foxminded.pskn.universitycms.service.university;

import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.foxminded.pskn.universitycms.converter.studentgroup.StudentGroupDTOToStudentGroupConverter;
import ua.foxminded.pskn.universitycms.converter.studentgroup.StudentGroupToStudentGroupDTOConverter;
import ua.foxminded.pskn.universitycms.customexception.StudentGroupEditException;
import ua.foxminded.pskn.universitycms.customexception.StudentGroupNotFoundException;
import ua.foxminded.pskn.universitycms.customexception.UniversityEditException;
import ua.foxminded.pskn.universitycms.customexception.UniversityNotFoundException;
import ua.foxminded.pskn.universitycms.dto.StudentGroupDTO;
import ua.foxminded.pskn.universitycms.model.university.StudentGroup;
import ua.foxminded.pskn.universitycms.model.university.University;
import ua.foxminded.pskn.universitycms.repository.university.StudentGroupRepository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class StudentGroupService {
    private final StudentGroupRepository studentGroupRepository;

    private final StudentGroupDTOToStudentGroupConverter toStudentGroupConverter;

    private final StudentGroupToStudentGroupDTOConverter toStudentGroupDTOConverter;

    public StudentGroupDTO saveStudentGroup(StudentGroupDTO studentGroupDTO) {
        log.info("Saving university: {}", studentGroupDTO.getStudentGroupName());
        if (StringUtils.isNotBlank(studentGroupDTO.getStudentGroupName())) {
            try{
                StudentGroup studentGroup = toStudentGroupConverter.convert(studentGroupDTO);
                studentGroup = studentGroupRepository.save(studentGroup);
                return toStudentGroupDTOConverter.convert(studentGroup);
            } catch (DataAccessException ex){
                throw new StudentGroupEditException("Student Group with name " + studentGroupDTO.getStudentGroupName() + " already exists.");
            }
        } else {
            throw new IllegalArgumentException("Student Group name cannot be blank.");
        }
    }

    @Transactional
    public void updateStudentGroupName(StudentGroupDTO studentGroupDTO){
        if (StringUtils.isNotBlank(studentGroupDTO.getStudentGroupName())) {
            log.info("Update student group: {}", studentGroupDTO.getStudentGroupName());
            studentGroupRepository.updateStudentGroupName(studentGroupDTO.getStudentGroupId(), studentGroupDTO.getStudentGroupName());
            if (!studentGroupRepository.existsById(studentGroupDTO.getStudentGroupId())) {
                throw new StudentGroupNotFoundException("Student Group not found with ID: " + studentGroupDTO.getStudentGroupId());
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

    public Page<StudentGroup> getAllStudentGroups(Pageable pageable){
        log.debug("Retrieving all students group with page number: {} and page size: {}", pageable.getPageNumber(), pageable.getPageSize());
        return studentGroupRepository.findAll(pageable);
    }

    public void deleteStudentGroup(Long id) {
        log.info("Deleting student group with ID: {}", id);
        studentGroupRepository.deleteById(id);
    }
}
