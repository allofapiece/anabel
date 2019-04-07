package com.pinwheel.anabel.service.notification;

import com.pinwheel.anabel.entity.User;
import com.pinwheel.anabel.external.category.Unit;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(locations = {
        "/application-test.properties",
        "/application-test-local.properties"
})
@Category(Unit.class)
class EmailNotifierUnitTest {
    @Autowired
    private EmailNotifier emailNotifier;

    @MockBean
    private NotificationMailSender mailSender;

    @Test
    public void shouldSendEmailNotificationMessage() {
        Mockito.doReturn(true)
                .when(mailSender)
                .send(any(EmailNotificationMessage.class));

        User user = new User();
        user.setEmail("test@email.com");

        EmailNotificationMessage message = EmailNotificationMessage.builder()
                .build();

        emailNotifier.send(user, message);

        Mockito.verify(mailSender, Mockito.times(1)).send(any(EmailNotificationMessage.class));
    }
}