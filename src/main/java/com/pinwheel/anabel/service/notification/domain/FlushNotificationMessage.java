package com.pinwheel.anabel.service.notification.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Flush Notification Message. Represents bootstrap alert messages.
 *
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FlushNotificationMessage extends NotificationMessage {
    /**
     * Flush status. Represents
     */
    private FlushStatus status = FlushStatus.INFO;

    private Object[] args;

    /**
     * View model for filling template.
     */
    private Model model;

    /**
     * Redirect attributes. Needs for setting flush attributes in actions, which redirects to another page.
     */
    private RedirectAttributes redirectAttributes;

    /**
     * Http servlet request. Needs for setting attributes in session in specific cases.
     */
    private HttpServletRequest request;

    /**
     * Constructor.
     *
     * @param message            message text.
     * @param status             status.
     * @param model              view model.
     * @param redirectAttributes view model.
     * @param request            view model.
     */
    @Builder
    public FlushNotificationMessage(String message, FlushStatus status, Object[] args, Model model,
                                    RedirectAttributes redirectAttributes, HttpServletRequest request) {
        super(message);
        this.status = status;
        this.args = args;
        this.model = model;
        this.redirectAttributes = redirectAttributes;
        this.request = request;
    }
}
