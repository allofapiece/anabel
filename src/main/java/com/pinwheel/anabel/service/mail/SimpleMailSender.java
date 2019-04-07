package com.pinwheel.anabel.service.mail;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Simple Mail Sender. Implements Mail Sender. Service for sending emails.
 *
 * @author Listratenko Stanislav
 * @version 1.0.0
 */
@Getter
@Setter
@RequiredArgsConstructor
@Service
public class SimpleMailSender implements MailSender {
    /**
     * Injection of the {@link JavaMailSender} bean. This been set up in {@link com.pinwheel.anabel.config.MailConfig}
     * configuration.
     */
    private final JavaMailSender mailSender;

    /**
     * Injection of the {@link Configuration} bean. This been set up in
     * {@link com.pinwheel.anabel.config.FreemarkerConfig} configuration.
     */
    private final Configuration freemarkerConfig;

    /**
     * Logger.
     */
    private Logger logger = LoggerFactory.getLogger(SimpleMailSender.class);

    /**
     * This template name will be used if template name is not set.
     */
    private String generalTemplateName = "general.ftl";

    /**
     * Application name for using in some templates.
     */
    @Value("${application.name}")
    private String applicationName;

    /**
     * {@inheritDoc}
     */
    public boolean send(String to, String subject, String templateName, Map<String, Object> model, int multiPartMode,
                        Charset charset) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper;

        try {
            helper = new MimeMessageHelper(message, multiPartMode, charset.name());

            model.put("applicationName", applicationName);

            Template template = freemarkerConfig.getTemplate(templateName);
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

            helper.setTo(to);
            helper.setText(html, true);
            helper.setSubject(subject);

            mailSender.send(message);
        } catch (Exception e) {
            logger.error("An error occurred while sending email.", e);
            return false;
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    public boolean send(String to, String subject, String message) {
        Map<String, Object> model = new HashMap<>();
        model.put("text", message);

        return this.send(to, subject, generalTemplateName, model,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8);
    }
}
