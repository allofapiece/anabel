package com.pinwheel.anabel.service.mail;

import com.icegreen.greenmail.util.GreenMailUtil;
import com.pinwheel.anabel.external.category.Unit;
import com.pinwheel.anabel.external.rule.SmtpServer;
import com.pinwheel.anabel.service.notification.EmailNotificationMessage;
import com.pinwheel.anabel.service.notification.NotificationMailSender;
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
public class NotificationMailSenderUnitTest {

    @Autowired
    private NotificationMailSender mailSender;

    @RegisterExtension
    static SmtpServer smtpServer = new SmtpServer(2525);

    @Test
    public void shouldSendValidEmailNotificationWithFullData() throws MessagingException {
        Map<String, Object> map = new HashMap<>();
        EmailNotificationMessage message = EmailNotificationMessage.builder()
                .message("Nice")
                .charset(StandardCharsets.UTF_8)
                .multiPartMode(MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED)
                .subject("Anabel")
                .to("testemail@email.com")
                .template("test.ftl")
                .model(map)
                .build();

        assertTrue(mailSender.send(message));

        MimeMessage[] receivedMessages = smtpServer.getMessages();
        assertEquals(1, receivedMessages.length);

        MimeMessage receivedMessage = receivedMessages[0];

        assertEquals(message.getSubject(), receivedMessage.getSubject());

        String content = GreenMailUtil.toString(receivedMessage);

        assertTrue(content.contains("Nice"));
        assertTrue(content.contains("Testing Email"));
        assertEquals(message.getTo(), receivedMessage.getAllRecipients()[0].toString());
    }
}
