package ua.foxminded.pskn.universitycms.controller.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;
import ua.foxminded.pskn.universitycms.model.user.Student;
import ua.foxminded.pskn.universitycms.service.user.StudentService;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Test
    void testStudentPage() throws Exception {
        Student student = new Student();
        student.setUserId(1L);
        student.setGroupId(3);

        Page<Student> studentPage = new PageImpl<>(Collections.singletonList(student));
        when(studentService.getAllStudents(any())).thenReturn(studentPage);

        mockMvc.perform(get("/students"))
            .andExpect(status().isOk())
            .andExpect(view().name("/users/students"))
            .andExpect(model().attributeExists("students"))
            .andExpect(model().attribute("students", studentPage.getContent()))
            .andExpect(model().attributeExists("currentPage"))
            .andExpect(model().attribute("currentPage", 0))
            .andExpect(model().attributeExists("totalPages"))
            .andExpect(model().attribute("totalPages", studentPage.getTotalPages()));
    }
}


