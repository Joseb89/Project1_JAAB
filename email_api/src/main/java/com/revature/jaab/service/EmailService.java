package com.revature.jaab.service;

import com.revature.jaab.dto.FormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendAdminEmail(FormDTO formDTO) throws MailException {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo("eldarion1989@gmail.com");
        mailMessage.setFrom("jbarr89@yahoo.com");
        mailMessage.setSubject("New Reimbursement Request");
        mailMessage.setText("A new request has come from " + formDTO.getFirstName() + " " + formDTO.getLastName() +
                "\n" + "ID is: " + formDTO.getFormID());

        javaMailSender.send(mailMessage);
    }

    public void sendEmployeeEmail(FormDTO formDTO) throws MailException {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo("jbarr89@yahoo.com");
        mailMessage.setFrom("eldarion1989@gmail.com");
        mailMessage.setSubject("Your Reimbursement Status");
        mailMessage.setText("Your reimbursement for Form No. " + formDTO.getFormID()
                + " has been " + formDTO.getRequestStatus());

        javaMailSender.send(mailMessage);

    }
}
