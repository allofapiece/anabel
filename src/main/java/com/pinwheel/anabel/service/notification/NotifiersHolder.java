package com.pinwheel.anabel.service.notification;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

import java.util.Map;

/**
 * Notifiers holder. Contains mapping of notifiers where keys are notifiers names.
 *
 * @author Listratenko Stanislav
 * @version 1.0.0
 */
@Builder
@Getter
@Setter
public class NotifiersHolder {
    /**
     * Map of {@link Notifier notifiers}.
     */
    @Singular
    private Map<String, Notifier> notifiers;
}
