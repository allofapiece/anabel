package com.pinwheel.anabel.service.notification.domain;

import com.fasterxml.jackson.annotation.JsonView;
import com.pinwheel.anabel.entity.Views;
import lombok.*;

import java.util.LinkedList;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@JsonView(Views.WithMeta.class)
public class Alert extends NotificationMessage {
    private FlushStatus type = FlushStatus.SUCCESS;

    private String code;

    /**
     * Strategy of alert. Uses for specifying client side rendering of specific alerts. For example:
     * `alert.signup.verifying`
     *
     * That says for client applications what need render for this alert.
     */
    private String strategy;

    private List<AlertAction> actions = new LinkedList<>();

    public Alert(String message, String code, String strategy, FlushStatus type) {
        super(message);
        this.code = code;
        this.strategy = strategy;
        this.type = type;
    }

    public Alert(String message, String code, String strategy, FlushStatus type, List<AlertAction> actions) {
        this(message, code, strategy, type);
        this.actions = actions;
    }

    public void addAction(AlertAction action) {
        this.actions.add(action);
    }
}
