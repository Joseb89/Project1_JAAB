package com.jaab.revature.controller;

import com.jaab.revature.dto.FormDTO;
import com.jaab.revature.dto.UserDTO;
import com.jaab.revature.model.Form;
import com.jaab.revature.model.User;
import com.jaab.revature.service.FormService;
import com.jaab.revature.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.rmi.ServerException;
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
    public ResponseEntity<Mono<FormDTO>> emailForm(HttpServletResponse response, @ModelAttribute("form") Form form,
                                                   @PathVariable Integer employeeId) throws IOException {
        int id = formService.createForm(form, employeeId);
        FormDTO formDTO = formService.getFormById(id);
        Mono<FormDTO> email = sendEmail(formDTO, "/admin/email");
        response.sendRedirect("submittedForm/" + formDTO.getFormId());
        return ResponseEntity.ok(email);
    }

    @GetMapping("employee/submittedForm/{formId}")
    public String submitForm(Model model, @PathVariable Integer formId) {
        showForm(formId, model);
        return "forms/submitted_form";
    }

    @GetMapping("admin/{employeeId}")
    public String loadAdminHome(Model model, @PathVariable Integer employeeId) {
        User admin = userService.getUserById(employeeId);
        Set<UserDTO> employees = userService.getUsersBySupervisor(admin.getFirstName() + " " + admin.getLastName());
        model.addAttribute("employees", employees);
        return "users/admin_home";
    }

    @GetMapping("admin/forms/{employeeId}")
    public String getFormsByEmployeeId(Model model, @PathVariable Integer employeeId){
        Set<FormDTO> forms = formService.getFormsByEmployeeId(employeeId);
        model.addAttribute("forms", forms);
        return "forms/employee_forms";
    }

    @GetMapping("admin/approveForm/{formId}")
    public String formStatus(Model model, @PathVariable Integer formId){
        showForm(formId, model);
        return "forms/approve_form";
    }

    @PatchMapping("admin/approveForm/{formId}")
    public ResponseEntity<Mono<FormDTO>> updateForm(HttpServletResponse response,
                                                    @ModelAttribute("formDTO") FormDTO formDTO,
                                                    @PathVariable Integer formId) throws IOException {
        formService.updateStatus(formId, formDTO.getRequestStatus());
        Mono<FormDTO> email = sendEmail(formDTO, "/employee/email");
        response.sendRedirect("/admin/updatedForm/" + formDTO.getFormId());
        return ResponseEntity.ok(email);
    }

    @GetMapping("admin/updatedForm/{formId}")
    public String getUpdatedForm(Model model, @PathVariable Integer formId){
        showForm(formId, model);
        return "forms/form_status";
    }

    private Mono<FormDTO> sendEmail(FormDTO formDTO, String uri){

        return webClient
                .post()
                .uri(uri)
                .body(Mono.just(formDTO), FormDTO.class)
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError,
                        response -> response.bodyToMono(String.class).map(ServerException::new))
                .onStatus(HttpStatus::is4xxClientError,
                        response -> response.bodyToMono(String.class).map(Exception::new))
                .bodyToMono(FormDTO.class);
    }

    private void showForm(Integer formId, Model model){
        FormDTO formDTO = formService.getFormById(formId);
        model.addAttribute("formDTO", formDTO);
    }
}
