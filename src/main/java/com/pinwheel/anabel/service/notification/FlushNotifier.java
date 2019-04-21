package com.pinwheel.anabel.service.notification;

import com.pinwheel.anabel.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Notifier for setting frontend alert messages. Alert messages represent bootstrap alert messages.
 *
 * @author Listratenko Stanislav
 * @version 1.0.0
 * @see <a href="https://getbootstrap.com/docs/4.0/components/alerts/">Bootstrap alerts.</a>
 */
@Service
public class FlushNotifier implements Notifier {
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean send(User user, NotificationMessage message) {
        return send(user, (FlushNotificationMessage) message);
    }

    /**
     * Sets alert status and message to view {@link org.springframework.ui.Model}. If passed null as user parameter or
     * user is a current user message will be set in a simple model view.
     *
     * @param user    target user.
     * @param message text message.
     * @return whether flush message has been set.
     */
    public boolean send(User user, FlushNotificationMessage message) {
        if (message.getModel() != null) {
            message.getModel().addAttribute("flushStatus", message.getStatus().name().toLowerCase());
            message.getModel().addAttribute("flushMessage", message.getMessage());

            return true;
        }

        if (message.getRedirectAttributes() != null) {
            message.getRedirectAttributes().addFlashAttribute("flushStatus", message.getStatus().name().toLowerCase());
            message.getRedirectAttributes().addFlashAttribute("flushMessage", message.getMessage());

            return true;
        }

        if (message.getRequest() != null) {
            HttpSession session = message.getRequest().getSession(true);

            session.setAttribute("flushStatus", message.getStatus().name().toLowerCase());
            session.setAttribute("flushMessage", message.getMessage());

            return true;
        }

        return false;
    }

    /**
     * Returns current user object.
     *
     * @return current user object.
     */
    protected User currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return null;
        }

        Object principal = authentication.getPrincipal();

        return principal instanceof String ? null : (User) principal;
    }

    /**
     * Determines whether user is current logged in.
     *
     * @param user target user.
     * @return whether user is current logged in.
     */
    protected boolean isCurrentUser(User user) {
        return user.equals(currentUser());
    }
}
