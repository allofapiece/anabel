package com.pinwheel.anabel.event;

import com.pinwheel.anabel.entity.User;
import org.springframework.context.ApplicationEvent;

/**
 * Event that describes moment after registration complete.
 *
 * @version 1.0
 */
@SuppressWarnings("serial")
public class OnRegistrationCompleteEvent extends ApplicationEvent {
    /**
     * Event related user.
     */
    private final User user;

    /**
     * Constructor.
     *
     * @param user event related user.
     */
    public OnRegistrationCompleteEvent(final User user) {
        super(user);
        this.user = user;
    }

    /**
     * Gets event related user.
     *
     * @return event related user.
     */
    public User getUser() {
        return user;
    }
}
