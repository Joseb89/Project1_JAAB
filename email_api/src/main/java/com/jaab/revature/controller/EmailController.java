package com.jaab.revature.controller;

import com.jaab.revature.dto.FormDTO;
import com.jaab.revature.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/adminEmail")
    public void sendEmailToAdmin(@RequestBody FormDTO formDTO){

        try {
            emailService.sendAdminEmail(formDTO);
        } catch (MailException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/employeeEmail")
    public void sendEmailToEmployee(@RequestBody FormDTO formDTO){

        try {
            emailService.sendEmployeeEmail(formDTO);
        } catch (MailException e) {
            e.printStackTrace();
        }
    }

}
