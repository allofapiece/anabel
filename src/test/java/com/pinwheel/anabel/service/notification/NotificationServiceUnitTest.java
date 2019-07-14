package com.pinwheel.anabel.service.notification;


import com.pinwheel.anabel.entity.Status;
import com.pinwheel.anabel.entity.User;
import com.pinwheel.anabel.external.category.Unit;
import com.pinwheel.anabel.service.notification.domain.*;
import com.pinwheel.anabel.service.notification.notifier.EmailNotifier;
import com.pinwheel.anabel.service.notification.notifier.FlushNotifier;
import com.pinwheel.anabel.service.notification.notifier.WebNotifier;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.ExtendedModelMap;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("non-async")
@TestPropertySource(locations = {
        "/application-test.properties",
        "/application-test-local.properties"
})
@Category(Unit.class)
public class NotificationServiceUnitTest {
    @Autowired
    private NotificationService notificationService;

    @MockBean
    private NotifierResolver notifierResolver;

    @MockBean
    private EmailNotifier emailNotifier;

    @MockBean
    private WebNotifier webNotifier;

    @MockBean
    private FlushNotifier flushNotifier;

    @Test
    public void shouldPutAndSendEmailNotificationMessage() {
        User user = new User();
        user.setId(1L);

        EmailNotificationMessage emailMessage = EmailNotificationMessage.builder()
                .to("test@email.com")
                .message("Text")
                .build();

        Mockito.doReturn(true).when(emailNotifier).send(user, emailMessage);
        Mockito.doReturn(emailNotifier).when(notifierResolver).resolve("email");

        assertTrue(notificationService.send(Notification.builder()
                .put("email", user, emailMessage)
                .build()));

        Mockito.verify(notifierResolver, Mockito.times(1)).resolve("email");
        Mockito.verify(emailNotifier, Mockito.times(1)).send(user, emailMessage);
    }

    @Test
    public void shouldPutAndSendSeveralEmailNotificationMessages() {
        User user = new User();
        user.setId(1L);

        User user2 = new User();
        user2.setId(2L);

        EmailNotificationMessage emailMessage = EmailNotificationMessage.builder()
                .to("test@email.com")
                .message("Text")
                .build();

        Mockito.doReturn(true).when(emailNotifier).send(any(User.class), eq(emailMessage));
        Mockito.doReturn(emailNotifier).when(notifierResolver).resolve("email");

        assertTrue(notificationService.send(Notification.builder()
                .put("email", Arrays.asList(user, user2), emailMessage)
                .build()));

        Mockito.verify(notifierResolver, Mockito.times(1)).resolve("email");
        Mockito.verify(emailNotifier, Mockito.times(2)).send(any(User.class), eq(emailMessage));
    }

    @Test
    public void shouldReturnFalseIfAtLeastOneNotifierIncorrect() {
        User user = new User();
        user.setId(1L);

        User user2 = new User();
        user2.setId(2L);

        EmailNotificationMessage emailMessage = new EmailNotificationMessage();
        FlushNotificationMessage flushMessage = new FlushNotificationMessage();

        Mockito.doReturn(true).when(emailNotifier).send(any(User.class), any(EmailNotificationMessage.class));
        Mockito.doReturn(false).when(flushNotifier).send(any(User.class), any(FlushNotificationMessage.class));
        Mockito.doReturn(user).when(flushNotifier).currentUser();

        Mockito.doReturn(emailNotifier).when(notifierResolver).resolve("email");
        Mockito.doReturn(flushNotifier).when(notifierResolver).resolve("flush");

        assertFalse(notificationService.send(Notification.builder()
                .put("email", user, emailMessage)
                .put("flush", user, flushMessage)
                .build()));

        Mockito.verify(notifierResolver, Mockito.times(2)).resolve(anyString());

        Mockito.verify(emailNotifier, Mockito.times(1)).send(any(User.class), eq(emailMessage));
        Mockito.verify(flushNotifier, Mockito.times(1)).send(eq(user), any(NotificationMessage.class));
    }

    @Test
    public void shouldFilterUsersByUserPredicates() {
        User user = new User();
        user.setId(1L);
        user.setStatus(Status.ACTIVE);

        User user2 = new User();
        user2.setStatus(Status.ACTIVE);

        User user3 = new User();
        user3.setStatus(Status.DELETED);

        List<User> list = new LinkedList<>();
        list.add(user);
        list.add(user2);
        list.add(user3);

        EmailNotificationMessage emailMessage = new EmailNotificationMessage();
        WebNotificationMessage webMessage = new WebNotificationMessage();
        FlushNotificationMessage flushMessage = new FlushNotificationMessage();
        flushMessage.setModel(new ExtendedModelMap());

        Mockito.doReturn(emailNotifier).when(notifierResolver).resolve("email");
        Mockito.doReturn(webNotifier).when(notifierResolver).resolve("web");
        Mockito.doReturn(flushNotifier).when(notifierResolver).resolve("flush");

        Mockito.doReturn(true).when(emailNotifier).send(any(User.class), any(EmailNotificationMessage.class));
        Mockito.doReturn(true).when(webNotifier).send(any(User.class), any(WebNotificationMessage.class));
        Mockito.doReturn(true).when(flushNotifier).send(any(User.class), any(NotificationMessage.class));

        Predicate<User> condition = pUser -> pUser.getStatus().equals(Status.ACTIVE);
        Predicate<User> condition2 = pUser -> pUser.getId() != null;

        assertTrue(notificationService.send(Notification.builder()
                .put("email", list, emailMessage)
                .put("web", list, webMessage)
                .put("flush", list, flushMessage)
                .condition(condition)
                .condition(condition2)
                .build()));

        Mockito.verify(emailNotifier, Mockito.times(1)).send(eq(user), eq(emailMessage));
        Mockito.verify(webNotifier, Mockito.times(1)).send(eq(user), eq(webMessage));
        Mockito.verify(flushNotifier, Mockito.times(1)).send(eq(user), any(NotificationMessage.class));
    }

    @Test
    public void shouldSendMessagesAsynchronously() {
        User user = new User();
        user.setId(1L);

        EmailNotificationMessage emailMessage = new EmailNotificationMessage();
        WebNotificationMessage webMessage = new WebNotificationMessage();
        FlushNotificationMessage flushMessage = new FlushNotificationMessage();

        Mockito.doReturn(CompletableFuture.completedFuture(true)).when(emailNotifier).sendAsync(any(User.class), any(EmailNotificationMessage.class));
        Mockito.doReturn(CompletableFuture.completedFuture(true)).when(webNotifier).sendAsync(any(User.class), any(WebNotificationMessage.class));
        Mockito.doReturn(CompletableFuture.completedFuture(true)).when(flushNotifier).sendAsync(any(User.class), any(NotificationMessage.class));

        Mockito.doReturn(emailNotifier).when(notifierResolver).resolve("email");
        Mockito.doReturn(webNotifier).when(notifierResolver).resolve("web");
        Mockito.doReturn(flushNotifier).when(notifierResolver).resolve("flush");

        notificationService.sendAsync(Notification.builder()
                .put("email", user, emailMessage)
                .put("web", user, webMessage)
                .put("flush", user, flushMessage)
                .build());

        Mockito.verify(notifierResolver, Mockito.times(1)).resolve("email");
        Mockito.verify(emailNotifier, Mockito.times(1)).sendAsync(eq(user), eq(emailMessage));
        Mockito.verify(webNotifier, Mockito.times(1)).sendAsync(eq(user), eq(webMessage));
        Mockito.verify(flushNotifier, Mockito.times(1)).sendAsync(eq(user), any(NotificationMessage.class));
    }
}
