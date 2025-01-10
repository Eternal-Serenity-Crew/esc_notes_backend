package com.esc.escnotesbackend.controllers;

import com.esc.escnotesbackend.dto.mail.SendEmailDTO;
import com.esc.escnotesbackend.services.MailerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mailer")
public class MailerController {
    private final MailerService mailerService;

    public MailerController(MailerService mailerService) {
        this.mailerService = mailerService;
    }

    @PostMapping("/send")
    public String sendMail(@RequestBody SendEmailDTO sendEmailDTO) {
        mailerService.sendMail(sendEmailDTO.to(), sendEmailDTO.subject(), sendEmailDTO.body());
        return "Good";
    }
}
