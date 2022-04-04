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
    public void loadFormTest() throws Exception {
        int employeeId = 8;

        mockMvc.perform(MockMvcRequestBuilders
                .get("/employee/" + employeeId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("forms/form"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("form"));
    }

    @Test
    public void loadAdminHomeTest() throws Exception {
        int adminId = 3;

        mockMvc.perform(MockMvcRequestBuilders
                .get("/admin/" + adminId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("users/admin_home"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("employees"));
    }

    @Test
    public void getFormsByEmployeeIdTest() throws Exception {
        int employeeId = 7;

        mockMvc.perform(MockMvcRequestBuilders
                .get("/admin/forms/" + employeeId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("forms/employee_forms"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("forms"));
    }

    @Test
    public void formStatusTest() throws Exception {
        int formId = 26;

        mockMvc.perform(MockMvcRequestBuilders
                .get("/admin/approveForm/" + formId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("forms/approve_form"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("formDTO"));
    }
}
