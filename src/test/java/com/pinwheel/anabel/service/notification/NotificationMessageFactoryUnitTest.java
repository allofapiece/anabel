package com.pinwheel.anabel.service.notification;

import com.pinwheel.anabel.entity.User;
import com.pinwheel.anabel.entity.VerificationToken;
import com.pinwheel.anabel.external.category.Unit;
import com.pinwheel.anabel.service.notification.domain.EmailNotificationMessage;
import com.pinwheel.anabel.service.notification.domain.NotificationMessage;
import com.pinwheel.anabel.service.notification.factory.EmailNotificationMessageFactory;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(locations = {
        "/application-test.properties",
        "/application-test-local.properties"
})
@Category(Unit.class)
class NotificationMessageFactoryUnitTest {
    @Autowired
    private EmailNotificationMessageFactory messageFactory;

    @Test
    public void shouldCreateSignupMessageAndSaveInCache() throws InvocationTargetException, IllegalAccessException {
        User user = new User();
        user.addVerificationToken(new VerificationToken("validToken"));
        assertThat(messageFactory.create("signup", user), instanceOf(NotificationMessage.class));
        assertTrue(messageFactory.getCache().size() != 0);

        assertTrue(messageFactory.getCache().containsKey(messageFactory.getClass().getName()));

        Map<String, NotificationMessage> factoryCache = messageFactory.getCache().get(messageFactory.getClass().getName());

        assertTrue(factoryCache.containsKey("signup"));
        assertThat(factoryCache.get("signup"), instanceOf(EmailNotificationMessage.class));
    }
}