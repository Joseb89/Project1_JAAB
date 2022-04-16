package com.jaab.revature.service;

import com.jaab.revature.dto.FormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * This class is a service to prepare an email for sending from data to either an admin or employee from the main api.
 * @author Joseph Barr
 */
@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final WebClient webClient;

    @Autowired
    public EmailService(JavaMailSender javaMailSender, WebClient.Builder webClientBuilder) {
        this.javaMailSender = javaMailSender;
        this.webClient = webClientBuilder.baseUrl("http://mainapi:8080").build();
    }

    /**
     * Prepares and sends an email to an admin user
     * @param formDTO - The form data to be emailed
     * @throws MailException if email cannot be composed
     */
    public void sendAdminEmail(FormDTO formDTO) throws MailException {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(getEmail(formDTO, "/adminEmail/"));
        mailMessage.setSubject("New Reimbursement Request");
        mailMessage.setText("A new request has come from " + formDTO.getFirstName() + " " + formDTO.getLastName() +
                "\n" + "ID is: " + formDTO.getFormId());

        javaMailSender.send(mailMessage);
    }

    /**
     * Prepares and sends an email to an employee
     * @param formDTO - The form data to be emailed
     * @throws MailException if email cannot be composed
     */
    public void sendEmployeeEmail(FormDTO formDTO) throws MailException {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(getEmail(formDTO, "/employeeEmail/"));
        mailMessage.setSubject("Your Reimbursement Status");
        mailMessage.setText("Your reimbursement for Form No. " + formDTO.getFormId()
                + " has been " + formDTO.getRequestStatus());

        javaMailSender.send(mailMessage);

    }

    /**
     * Retrieves the user's email address from the main api
     * @param formDTO - the submitted form which contains the user's id
     * @param uri - the REST endpoint to call
     * @return - user's email address
     */
    private String getEmail(FormDTO formDTO, String uri) {
        return
                webClient.get()
                        .uri(uri + formDTO.getEmployeeId())
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();
    }


}
