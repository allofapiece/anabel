package com.pinwheel.anabel.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Email Sender. Service for sending emails.
 *
 * @author Listratenko Stanislav
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class MailSender {
    /**
     * Injection of {@link JavaMailSender} bean.
     */
    private final JavaMailSender mailSender;

    /**
     * Email of sending mail server account.
     */
    @Value("${spring.mail.username}")
    private String username;

    /**
     * Sends email.
     *
     * @param emailTo target email.
     * @param subject subject of the email.
     * @param message message of the email.
     */
    public void send(String emailTo, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(username);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }
}
