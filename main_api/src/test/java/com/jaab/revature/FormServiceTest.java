package com.jaab.revature;

import com.jaab.revature.dto.FormDTO;
import com.jaab.revature.model.Form;
import com.jaab.revature.service.FormService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@SpringBootTest
public class FormServiceTest {

    private FormDTO formDTO;
    private Set<FormDTO> employeeForms;

    @Autowired
    private FormService formService;

    @BeforeEach
    public void init(){
        formDTO = formService.getFormById(12);
        employeeForms = formService.getFormsByEmployeeId(7);
    }

    @Test
    public void loadFormTest_Success() {
        Form form = new Form();
        formService.loadForm(form, 7);
        Assertions.assertEquals(form.getFirstName(), "Wynne");
    }

    @Test
    public void loadFormTest_Fail() {
        Form form = new Form();
        formService.loadForm(form, 6);
        Assertions.assertNotEquals(form.getFirstName(), "Donal");
    }

    @Test
    public void getFormByIdTest_Success() {
        Assertions.assertEquals("Sutherland", formDTO.getLastName());
    }

    @Test
    public void getFormByIdTest_Fail() {
        Assertions.assertNotEquals("Solas", formDTO.getFirstName());
    }

    @Test
    public void getFormsByEmployeeIdTest_NotEmpty() {
        Assertions.assertFalse(employeeForms.isEmpty());
    }

    @Test
    public void getFormsByEmployeeIdTest_Success() {
        for (FormDTO form : employeeForms)
            Assertions.assertEquals("Wynne", form.getFirstName());
    }

    @Test
    public void getFormsByEmployeeIdTest_Fail() {
        for (FormDTO form : employeeForms)
            Assertions.assertNotEquals("Chainbreaker", form.getLastName());
    }
}
