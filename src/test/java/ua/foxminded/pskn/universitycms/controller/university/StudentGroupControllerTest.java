package ua.foxminded.pskn.universitycms.controller.university;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;
import ua.foxminded.pskn.universitycms.model.university.StudentGroup;
import ua.foxminded.pskn.universitycms.service.university.StudentGroupService;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentGroupController.class)
class StudentGroupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentGroupService studentGroupService;

    @Test
    void testStudentGroupPage() throws Exception {
        StudentGroup studentGroup = new StudentGroup();
        studentGroup.setGroupId(1L);
        studentGroup.setGroupName("IC-72");

        Page<StudentGroup> studentGroupPage = new PageImpl<>(Collections.singletonList(studentGroup));
        when(studentGroupService.getAllStudentGroups(any())).thenReturn(studentGroupPage);

        mockMvc.perform(get("/studentgroup"))
            .andExpect(status().isOk())
            .andExpect(view().name("/university/studentgroup"))
            .andExpect(model().attributeExists("student_group"))
            .andExpect(model().attribute("student_group", studentGroupPage.getContent()))
            .andExpect(model().attributeExists("currentPage"))
            .andExpect(model().attribute("currentPage", 0))
            .andExpect(model().attributeExists("totalPages"))
            .andExpect(model().attribute("totalPages", studentGroupPage.getTotalPages()));
    }
}
