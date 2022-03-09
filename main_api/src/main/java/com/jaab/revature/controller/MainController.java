package com.jaab.revature.controller;

import com.jaab.revature.dto.FormDTO;
import com.jaab.revature.model.Form;
import com.jaab.revature.service.FormService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller
public class MainController {

    private final FormService formService;

    @Autowired
    public MainController(FormService formService) {
        this.formService = formService;
    }

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/employees/{employeeId}")
    public String loadForm(Model model, @PathVariable Integer employeeId){
        Form form = new Form();
        formService.loadForm(form, employeeId);
        model.addAttribute("form", form);
        return "form";
    }

    @PostMapping("/employees/{employeeId}")
    public String submitForm(@ModelAttribute("form") Form form, @PathVariable Integer employeeId) {
        int id = formService.createForm(form, employeeId);
        FormDTO formDTO = formService.getFormByID(id);

        ResponseEntity<FormDTO> response = restTemplate.postForEntity("http://localhost:8081/admin/email", formDTO,
                null);

        if (response.getStatusCode().is5xxServerError())
            return "form";

        return "submitted_form";
    }

    @GetMapping("/admin/{formId}")
    public String formStatus(Model model, @PathVariable Integer formId){
        FormDTO formDTO = formService.getFormByID(formId);
        model.addAttribute("formDTO", formDTO);
        return "approve_form";
    }

    @PatchMapping("/admin/{formId}")
    public String updateForm(@ModelAttribute("formDTO") FormDTO formDTO, @PathVariable Integer formId) {
        FormDTO displayForm = formService.getFormByID(formId);
        BeanUtils.copyProperties(displayForm, formDTO);
        formService.updateStatus(formId, formDTO.getRequestStatus());
        return "form_status";
    }

}
