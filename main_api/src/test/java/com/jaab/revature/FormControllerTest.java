package com.jaab.revature;

import com.jaab.revature.controller.FormController;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class FormControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private FormController formController;

    @Before
    public void setup(){
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(formController).build();
    }

    @Test
    public void mockMvcNotNull() {
        Assertions.assertNotNull(mockMvc);
    }

    @Test
    @WithMockUser(username = "dreadwolf@yahoo.com", roles = "EMPLOYEE")
    public void loadFormTest_Success() throws Exception {
        int employeeId = 8;

        mockMvc.perform(MockMvcRequestBuilders
                .get("/employee/" + employeeId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("forms/form"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("form"));
    }

    @Test
    public void loadFormTest_Fail() throws Exception {
        int employeeId = 6;

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/employee/" + employeeId))
                        .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "championofkirkwall@gmail.com", roles = "ADMIN")
    public void loadAdminHomeTest_Success() throws Exception {
        int adminId = 4;

        mockMvc.perform(MockMvcRequestBuilders
                .get("/admin/" + adminId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("users/admin_home"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("employees"));
    }

    @Test
    public void loadAdminHomeTest_Fail() throws Exception {
        int adminId = 4;

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/admin/" + adminId))
                        .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "heroofferelden@yahoo.com", roles = "ADMIN")
    public void getFormsByEmployeeIdTest_Success() throws Exception {
        int employeeId = 2;

        mockMvc.perform(MockMvcRequestBuilders
                .get("/admin/forms/" + employeeId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("forms/employee_forms"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("forms"));
    }

    @Test
    public void getFormsByEmployeeIdTest_Fail() throws Exception {
        int employeeId = 2;

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/admin/forms/" + employeeId))
                        .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "grandinquisitor@gmail.com", roles = "ADMIN")
    public void formStatusTest_Success() throws Exception {
        int formId = 16;

        mockMvc.perform(MockMvcRequestBuilders
                .get("/admin/approveForm/" + formId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("forms/approve_form"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("formDTO"));
    }

    @Test
    public void formStatusTest_Fail() throws Exception {
        int formId = 16;

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/admin/approveForm/" + formId))
                        .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }
}
