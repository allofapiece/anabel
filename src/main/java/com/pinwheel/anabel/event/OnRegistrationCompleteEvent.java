package com.pinwheel.anabel.event;

import com.pinwheel.anabel.entity.User;
import org.springframework.context.ApplicationEvent;


@SuppressWarnings("serial")
public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private final User user;

    public OnRegistrationCompleteEvent(final User user) {
        super(user);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

}
