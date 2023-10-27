package ua.foxminded.pskn.universitycms.service.usercabinet;

import java.util.List;

import org.springframework.stereotype.Service;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ua.foxminded.pskn.universitycms.dto.StudentDTO;
import ua.foxminded.pskn.universitycms.dto.UserDTO;
import ua.foxminded.pskn.universitycms.model.university.StudentGroup;
import ua.foxminded.pskn.universitycms.model.usercabinetdata.StudentCabinetData;
import ua.foxminded.pskn.universitycms.service.university.StudentGroupService;
import ua.foxminded.pskn.universitycms.service.user.StudentService;
import ua.foxminded.pskn.universitycms.service.user.UserService;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserCabinetService {

    private final StudentGroupService studentGroupService;

    private final UserService userService;

    private final StudentService studentService;

    public StudentCabinetData getStudentCabinetData(String username) {
        if (StringUtils.isBlank(username)) {
            log.error("Username is blank");
            throw new IllegalArgumentException("Username can't be blank");
        }

        UserDTO user = userService.findStudentByUsername(username);

        StudentDTO student = studentService.getStudentByUserId(user.getUserId());

        StudentGroup groupName = studentGroupService.getStudentGroupById(student.getGroupId());
        List<StudentGroup> availableGroups = studentGroupService.getAllStudentGroups();

        StudentCabinetData cabinetData = new StudentCabinetData();
        cabinetData.setUsername(user.getUsername());
        cabinetData.setStudentId(student.getUserId());
        cabinetData.setStudentGroup(groupName.getGroupName());
        cabinetData.setAvailableGroups(availableGroups);
        cabinetData.setUserID(student.getUserId());

        log.info("Retrieved student cabinet data for username: {}", username);
        return cabinetData;
    }

}
