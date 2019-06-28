package com.pinwheel.anabel.service.notification;

import com.pinwheel.anabel.external.category.Unit;
import com.pinwheel.anabel.service.notification.exception.IllegalNotificationStateException;
import com.pinwheel.anabel.service.notification.notifier.EmailNotifier;
import com.pinwheel.anabel.service.notification.notifier.FlushNotifier;
import com.pinwheel.anabel.service.notification.notifier.WebNotifier;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(locations = {
        "/application-test.properties",
        "/application-test-local.properties"
})
@Category(Unit.class)
class SimpleNotifierResolverUnitTest {
    @Autowired
    private NotifiersHolder notifiersHolder;

    @Autowired
    private NotifierResolver notifierResolver;

    @Test
    public void shouldHaveNotifiersInNotifierHolder() {
        assertTrue(notifiersHolder.getNotifiers().containsKey("email"));
        assertTrue(notifiersHolder.getNotifiers().containsKey("web"));
        assertTrue(notifiersHolder.getNotifiers().containsKey("email"));

        assertThat(notifiersHolder.getNotifiers().get("email"), instanceOf(EmailNotifier.class));
        assertThat(notifiersHolder.getNotifiers().get("web"), instanceOf(WebNotifier.class));
        assertThat(notifiersHolder.getNotifiers().get("flush"), instanceOf(FlushNotifier.class));
    }

    @Test
    public void shouldResolveNotifiers() {
        assertThat(notifierResolver.resolve("email"), instanceOf(EmailNotifier.class));
        assertThat(notifierResolver.resolve("web"), instanceOf(WebNotifier.class));
        assertThat(notifierResolver.resolve("flush"), instanceOf(FlushNotifier.class));
    }

    @Test
    public void shoultThrowExceptionWhenNotifierNotFound() {
        IllegalNotificationStateException ex = assertThrows(
                IllegalNotificationStateException.class,
                () -> notifierResolver.resolve("nonexistentNotifier")
        );
        assertEquals(ex.getMessage(), "Cannot resolve notifier. Illegal notifier name.");
    }
}