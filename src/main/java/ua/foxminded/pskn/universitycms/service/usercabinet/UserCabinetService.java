package ua.foxminded.pskn.universitycms.service.usercabinet;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.foxminded.pskn.universitycms.model.university.StudentGroup;
import ua.foxminded.pskn.universitycms.model.user.Student;
import ua.foxminded.pskn.universitycms.model.user.User;
import ua.foxminded.pskn.universitycms.model.usercabinetdata.StudentCabinetData;
import ua.foxminded.pskn.universitycms.service.university.StudentGroupService;
import ua.foxminded.pskn.universitycms.service.user.StudentService;
import ua.foxminded.pskn.universitycms.service.user.UserService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserCabinetService {

    private final StudentGroupService studentGroupService;

    private final UserService userService;

    private final StudentService studentService;

    public StudentCabinetData getStudentCabinetData(String username) {
        if (StringUtils.isBlank(username)) {
            throw new IllegalArgumentException("Username cant be blank");
        }
        Optional<User> userOptional = userService.findStudentByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Optional<Student> studentOptional = studentService.getStudentByUserId(user.getUserId());

            if (studentOptional.isPresent()) {
                Student student = studentOptional.get();
                StudentGroup groupName = studentGroupService.getStudentGroupById((long) student.getGroupId());
                List<StudentGroup> availableGroups = studentGroupService.getAllStudentGroups();

                StudentCabinetData cabinetData = new StudentCabinetData();
                cabinetData.setUsername(user.getUsername());
                cabinetData.setStudentId(student.getUserId());
                cabinetData.setStudentGroup(groupName.getGroupName());
                cabinetData.setAvailableGroups(availableGroups);

                return cabinetData;
            }
        }

        throw new IllegalArgumentException("Student with username " + username + " not found.");
    }
}
