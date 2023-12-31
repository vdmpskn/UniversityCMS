package ua.foxminded.pskn.universitycms.controller.university;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import ua.foxminded.pskn.universitycms.dto.StudentGroupDTO;
import ua.foxminded.pskn.universitycms.service.university.StudentGroupService;

@WebMvcTest(StudentGroupController.class)
@WithMockUser(username = "a", password = "a", roles = "ADMIN")
class StudentGroupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentGroupService studentGroupService;

    @Test
    void shouldGetStudentGroupPage() throws Exception {
        StudentGroupDTO studentGroup = new StudentGroupDTO();
        studentGroup.setGroupId(1L);
        studentGroup.setGroupName("IC-72");

        Page<StudentGroupDTO> studentGroupPage = new PageImpl<>(Collections.singletonList(studentGroup));
        when(studentGroupService.getAllStudentGroups(any())).thenReturn(studentGroupPage);

        mockMvc.perform(get("/studentgroup"))
            .andExpect(status().isOk())
            .andExpect(view().name("/university/studentgroup"))
            .andExpect(model().attributeExists("studentGroup"))
            .andExpect(model().attribute("studentGroup", studentGroupPage.getContent()))
            .andExpect(model().attributeExists("currentPage"))
            .andExpect(model().attribute("currentPage", 0))
            .andExpect(model().attributeExists("totalPages"))
            .andExpect(model().attribute("totalPages", studentGroupPage.getTotalPages()));
    }

    @Test
    void shouldAddStudentGroup_Success() throws Exception {
        StudentGroupDTO studentGroupDTO = new StudentGroupDTO();

        mockMvc.perform(post("/studentgroup/add")
                .flashAttr("studentGroupDTO", studentGroupDTO)
                .with(csrf()))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/studentgroup"))
            .andExpect(flash().attributeExists("successStudentGroupMessage"));

        verify(studentGroupService, times(1)).saveStudentGroup(studentGroupDTO);
    }


    @Test
    void shouldDeleteStudentGroup_Success() throws Exception {
        StudentGroupDTO studentGroupDTO = new StudentGroupDTO();
        studentGroupDTO.setGroupId(1L);

        mockMvc.perform(post("/studentgroup/delete")
                .flashAttr("studentGroupDTO", studentGroupDTO)
                .with(csrf()))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/studentgroup"))
            .andExpect(flash().attributeExists("deleteStudentGroupMessage"));

        verify(studentGroupService, times(1)).deleteStudentGroup(1L);
    }


    @Test
     void shouldEditStudentGroup_Success() throws Exception {
        StudentGroupDTO studentGroupDTO = new StudentGroupDTO();

        mockMvc.perform(post("/studentgroup/edit")
                .flashAttr("studentGroupDTO", studentGroupDTO)
                .with(csrf()))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/studentgroup"))
            .andExpect(flash().attributeExists("editStudentGroupMessage"));

        verify(studentGroupService, times(1)).updateStudentGroupName(studentGroupDTO);
    }

}
