package com.jaab.revature.service;

import com.jaab.revature.dto.FormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final WebClient webClient;

    @Autowired
    public EmailService(JavaMailSender javaMailSender, WebClient.Builder webClientBuilder) {
        this.javaMailSender = javaMailSender;
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
    }

    public void sendAdminEmail(FormDTO formDTO) throws MailException {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(getEmail(formDTO, "/adminEmail/"));
        mailMessage.setSubject("New Reimbursement Request");
        mailMessage.setText("A new request has come from " + formDTO.getFirstName() + " " + formDTO.getLastName() +
                "\n" + "ID is: " + formDTO.getFormId());

        javaMailSender.send(mailMessage);
    }

    public void sendEmployeeEmail(FormDTO formDTO) throws MailException {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(getEmail(formDTO, "/employeeEmail/"));
        mailMessage.setSubject("Your Reimbursement Status");
        mailMessage.setText("Your reimbursement for Form No. " + formDTO.getFormId()
                + " has been " + formDTO.getRequestStatus());

        javaMailSender.send(mailMessage);

    }

    private String getEmail(FormDTO formDTO, String uri) {
        return
                webClient.get()
                        .uri(uri + formDTO.getEmployeeId())
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();
    }


}
