package com.jaab.revature.controller;

import com.jaab.revature.dto.FormDTO;
import com.jaab.revature.model.Form;
import com.jaab.revature.service.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

@RestController
public class FormController {

    @Value("${server.port}")
    int port;

    private final FormService formService;

    @Autowired
    public FormController(FormService formService) {this.formService = formService;}

    @Autowired
    RestTemplate restTemplate;

    @PostMapping(value = "/employees/{employeeId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Form> createForm(@RequestBody Form form, @PathVariable Integer employeeId) {
        int id = formService.createForm(form, employeeId);
        FormDTO formDTO = formService.getFormByID(id);

        ResponseEntity<FormDTO> response = restTemplate.postForEntity("http://localhost:8081/admin/email", formDTO,
                null);

        if (response.getStatusCode().is5xxServerError())
            return ResponseEntity.internalServerError().build();

        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/forms", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<FormDTO>> getAllForms(){
        Set<FormDTO> forms = formService.getAllForms();
        return ResponseEntity.ok(forms);
    }

    @GetMapping("employees/forms/{employeeId}")
    public ResponseEntity<Set<FormDTO>> getFormsByEmployeeID(@PathVariable Integer employeeId){
        Set<FormDTO> forms = formService.getFormsByEmployeeID(employeeId);
        return ResponseEntity.ok(forms);
    }

    @GetMapping("/forms/{formId}")
    public ResponseEntity<FormDTO> getFormByID(@PathVariable Integer formId) {
        FormDTO formDTO = formService.getFormByID(formId);
        return ResponseEntity.ok(formDTO);
    }

    @PatchMapping(value = "/forms/{formId}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FormDTO> updateForm(@PathVariable Integer formId, @RequestBody FormDTO formDTO){
        formDTO = formService.updateStatus(formId, formDTO.getRequestStatus());

        ResponseEntity<FormDTO> response = restTemplate.postForEntity("http://localhost:8081/employee/email",
                formDTO,
                null);

        if (response.getStatusCode().is5xxServerError())
            return ResponseEntity.internalServerError().build();

        return ResponseEntity.noContent().build();
    }
}
