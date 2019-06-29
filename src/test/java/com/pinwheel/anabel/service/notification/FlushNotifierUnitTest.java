package com.pinwheel.anabel.service.notification;

import com.pinwheel.anabel.external.category.Unit;
import com.pinwheel.anabel.service.notification.domain.FlushNotificationMessage;
import com.pinwheel.anabel.service.notification.domain.FlushStatus;
import com.pinwheel.anabel.service.notification.notifier.FlushNotifier;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(locations = {
        "/application-test.properties",
        "/application-test-local.properties"
})
@Category(Unit.class)
class FlushNotifierUnitTest {
    @Autowired
    private FlushNotifier flushNotifier;

    @Test
    public void shouldSetFlushNotificationMessageInViewModel() {
        FlushNotificationMessage message = FlushNotificationMessage.builder()
                .message("Test flush message")
                .model(new ExtendedModelMap())
                .status(FlushStatus.DANGER)
                .build();

        assertTrue(flushNotifier.send(null, message));
        Model model = message.getModel();

        assertTrue(model.containsAttribute("flushStatus"));
        assertTrue(model.containsAttribute("flushMessage"));

        Map<String, Object> modelMap = model.asMap();

        assertEquals(modelMap.get("flushStatus"), "danger");
        assertEquals(modelMap.get("flushMessage"), "Test flush message");
    }

    @Test
    public void shouldDetermineWhetherMessageIsI18nCode() {
        assertNotNull(flushNotifier.getPattern());
        assertFalse(flushNotifier.isI18n("Test message, that not code."));
        assertTrue(flushNotifier.isI18n("i18n.code"));
    }

    @Test
    public void shouldSendFlushNotificationMessageAsynchronously() throws ExecutionException, InterruptedException {
        FlushNotificationMessage message = FlushNotificationMessage.builder()
                .message("Test flush message")
                .model(new ExtendedModelMap())
                .status(FlushStatus.DANGER)
                .build();

        CompletableFuture<Boolean> ctf = flushNotifier.sendAsync(null, message);
        assertTrue(ctf.get());
    }
}
