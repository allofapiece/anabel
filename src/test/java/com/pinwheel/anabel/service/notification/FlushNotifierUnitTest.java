package com.pinwheel.anabel.service.notification;

import com.pinwheel.anabel.external.category.Unit;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
}
