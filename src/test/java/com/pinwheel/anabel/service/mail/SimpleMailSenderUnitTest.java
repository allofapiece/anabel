package com.pinwheel.anabel.service.mail;

import com.icegreen.greenmail.util.GreenMailUtil;
import com.pinwheel.anabel.external.category.Unit;
import com.pinwheel.anabel.external.rule.SmtpServer;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(locations = {
        "/application-test.properties",
        "/application-test-local.properties"
})
@Category(Unit.class)
class SimpleMailSenderUnitTest {
    @Autowired
    private MailSender mailSender;

    @RegisterExtension
    static SmtpServer smtpServer = new SmtpServer(2525);

    @Test
    public void shouldSendValidEmailMessage() throws MessagingException {
        Map<String, Object> map = new HashMap<>();
        map.put("text", "Payload");

        String subject = "Anabel";
        String to = "testemail@email.com";

        assertTrue(mailSender.send(
                to,
                subject,
                "test.ftl",
                map,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8
        ));

        MimeMessage[] receivedMessages = smtpServer.getMessages();
        assertEquals(1, receivedMessages.length);

        MimeMessage receivedMessage = receivedMessages[0];

        assertEquals(subject, receivedMessage.getSubject());

        String content = GreenMailUtil.toString(receivedMessage);

        assertTrue(content.contains("Payload"));
        assertTrue(content.contains("Testing Email"));
        assertEquals(to, receivedMessage.getAllRecipients()[0].toString());
    }

    @Test
    public void shouldSendValidEmailMessageWithDefaultValues() throws MessagingException {
        mailSender.setGeneralTemplateName("test.ftl");

        String subject = "Anabel";
        String to = "testemail@email.com";
        String message = "Payload";

        assertTrue(mailSender.send(to, subject, message));

        MimeMessage[] receivedMessages = smtpServer.getMessages();
        assertEquals(1, receivedMessages.length);

        MimeMessage receivedMessage = receivedMessages[0];

        assertEquals(subject, receivedMessage.getSubject());

        String content = GreenMailUtil.toString(receivedMessage);

        assertTrue(content.contains(message));
        assertTrue(content.contains("Testing Email"));
        assertEquals(to, receivedMessage.getAllRecipients()[0].toString());
    }
}