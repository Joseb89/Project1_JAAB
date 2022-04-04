package com.jaab.revature;

import com.jaab.revature.controller.ServiceController;
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
public class ServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ServiceController serviceController;

    @Before
    public void setup(){
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(serviceController).build();
    }

    @Test
    public void mockMvcNotNull() {
        Assertions.assertNotNull(mockMvc);
    }

    @Test
    public void getEmployeeEmailTest() throws Exception {
        int employeeId = 5;

        mockMvc.perform(MockMvcRequestBuilders
                .get("/employeeEmail/" + employeeId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("bringerofjustice@yahoo.com"));
    }

    @Test
    public void getAdminEmailTest() throws Exception {
        int employeeId = 9;

        mockMvc.perform(MockMvcRequestBuilders
                .get("/adminEmail/" + employeeId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("grandinquisitor@aol.com"));
    }
}
