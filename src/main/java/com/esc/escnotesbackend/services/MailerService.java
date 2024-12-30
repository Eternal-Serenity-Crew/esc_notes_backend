package com.esc.escnotesbackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailerService {
    private final JavaMailSender mailSender;

    @Autowired
    public MailerService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("esc-notes@yandex.ru");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
}
