package com.jaab.revature.controller;

import com.jaab.revature.dto.FormDTO;
import com.jaab.revature.dto.UserDTO;
import com.jaab.revature.model.Form;
import com.jaab.revature.model.User;
import com.jaab.revature.service.FormService;
import com.jaab.revature.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.rmi.ServerException;
import java.time.Duration;
import java.util.Set;

@Controller
public class FormController {

    private final FormService formService;
    private final UserService userService;
    private final WebClient webClient;

    @Autowired
    public FormController(FormService formService, UserService userService, WebClient.Builder webClientBuilder) {
        this.formService = formService;
        this.userService = userService;
        this.webClient = webClientBuilder.baseUrl("http://localhost:8081").build();
    }

    @GetMapping("employee/{employeeId}")
    public String loadForm(Model model, @PathVariable Integer employeeId){
        Form form = new Form();
        formService.loadForm(form, employeeId);
        model.addAttribute("form", form);
        return "forms/form";
    }

    @PostMapping("employee/{employeeId}")
    public String submitForm(@ModelAttribute("form") Form form, @PathVariable Integer employeeId) {
        int id = formService.createForm(form, employeeId);
        FormDTO formDTO = formService.getFormById(id);
        sendEmail(formDTO, "/admin/email");
        return "forms/submitted_form";
    }

    @GetMapping("/admin/{employeeId}")
    public String loadAdminHome(Model model, @PathVariable Integer employeeId) {
        User admin = userService.getUserById(employeeId);
        Set<UserDTO> employees = userService.getUsersBySupervisor(admin.getFirstName() + " " + admin.getLastName());
        model.addAttribute("employees", employees);
        return "users/admin_home";
    }

    @GetMapping("/admin/forms/{employeeId}")
    public String getFormsByEmployeeId(Model model, @PathVariable Integer employeeId){
        Set<FormDTO> forms = formService.getFormsByEmployeeId(employeeId);
        model.addAttribute("forms", forms);
        return "forms/employee_forms";
    }

    @GetMapping("/admin/approveForm/{formId}")
    public String formStatus(Model model, @PathVariable Integer formId){
        FormDTO formDTO = formService.getFormById(formId);
        model.addAttribute("formDTO", formDTO);
        return "forms/approve_form";
    }

    @PatchMapping("/admin/approveForm/{formId}")
    public String updateForm(@ModelAttribute("formDTO") FormDTO formDTO, @PathVariable Integer formId) {
        formService.updateStatus(formId, formDTO.getRequestStatus());
        sendEmail(formDTO, "/employee/email");
        return "forms/form_status";
    }


    private void sendEmail(FormDTO formDTO, String uri){
        webClient
                .post()
                .uri(uri)
                .body(Mono.just(formDTO), FormDTO.class)
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError,
                        response -> response.bodyToMono(String.class).map(ServerException::new))
                .onStatus(HttpStatus::is4xxClientError,
                        response -> response.bodyToMono(String.class).map(Exception::new))
                .bodyToMono(FormDTO.class)
                .timeout(Duration.ofMillis(10000L));
    }
}
