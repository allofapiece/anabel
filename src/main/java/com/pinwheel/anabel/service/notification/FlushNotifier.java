package com.pinwheel.anabel.service.notification;

import com.pinwheel.anabel.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.util.regex.Pattern;

/**
 * Notifier for setting frontend alert messages. Alert messages represent bootstrap alert messages.
 *
 * @author Listratenko Stanislav
 * @version 1.0.0
 * @see <a href="https://getbootstrap.com/docs/4.0/components/alerts/">Bootstrap alerts.</a>
 */
@Service
@RequiredArgsConstructor
public class FlushNotifier implements Notifier {
    private final MessageSource messageSource;

    private static final String I18N_PATTERN_STRING = "(\\.[\\s])|([A-Z]+[a-z\\s]+)";

    @Getter
    private Pattern pattern;

    @PostConstruct
    public void init() {
        pattern = Pattern.compile(I18N_PATTERN_STRING);
    }

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
        String text;
        boolean filtered = false;

        if (message.getMessage() != null) {
            String m = message.getMessage();
            Object[] args = message.getArgs();
            text = isI18n(m)
                    ? ((args != null && args.length != 0) ? localize(m, args) : localize(m))
                    : m;
        } else {
            return false;
        }

        if (message.getModel() != null) {
            message.getModel().addAttribute("flushStatus", message.getStatus().name().toLowerCase());
            message.getModel().addAttribute("flushMessage", text);

            filtered = true;
        }

        if (message.getRedirectAttributes() != null) {
            message.getRedirectAttributes().addFlashAttribute("flushStatus", message.getStatus().name().toLowerCase());
            message.getRedirectAttributes().addFlashAttribute("flushMessage", text);

            filtered = true;
        }

        if (message.getRequest() != null) {
            HttpSession session = message.getRequest().getSession(true);

            session.setAttribute("flushStatus", message.getStatus().name().toLowerCase());
            session.setAttribute("flushMessage", text);

            filtered = true;
        }

        return filtered;
    }

    public String localize(String code) {
        return localize(code, new Object[0]);
    }

    public String localize(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

    public boolean isI18n(String message) {
        if (message.indexOf(' ') != -1) {
            return false;
        }

        return !pattern.matcher(message).find();
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
