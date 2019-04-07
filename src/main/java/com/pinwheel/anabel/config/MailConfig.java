package com.pinwheel.anabel.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Mail and {@link JavaMailSender JavaMailSender} configuration.
 *
 * @author Listratenko Stanislav
 * @version 1.0.0
 */
@Configuration
@RequiredArgsConstructor
public class MailConfig {
    /**
     * Host of sending emails service.
     */
    @Value("${spring.mail.host}")
    private String host;

    /**
     * Username of sending emails service.
     */
    @Value("${spring.mail.username}")
    private String username;

    /**
     * Password of sending emails service
     */
    @Value("${spring.mail.password}")
    private String password;

    /**
     * Port of sending emails service
     */
    @Value("${spring.mail.port}")
    private int port;

    /**
     * Protocol of sending emails service
     */
    @Value("${spring.mail.protocol}")
    private String protocol;

    /**
     * Determines whether debug on.
     */
    @Value("${mail.debug}")
    private String debug;

    /**
     * Bean and configuration of {@link JavaMailSender}.
     *
     * @return {@link JavaMailSender} bean.
     */
    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties properties = mailSender.getJavaMailProperties();

        properties.setProperty("mail.transport.protocol", protocol);
        properties.setProperty("mail.debug", debug);

        return mailSender;
    }
}
