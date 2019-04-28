package com.pinwheel.anabel.service.handler.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

/**
 * Default authentication failure handler. Implementation of {@link SimpleUrlAuthenticationFailureHandler} class.
 *
 * @version 1.0
 */
@Component("authenticationFailureHandler")
@RequiredArgsConstructor
public class DefaultAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    /**
     * Inject of {@link MessageSource} bean.
     */
    private final MessageSource messages;

    /**
     * Inject of {@link LocaleResolver} bean.
     */
    private final LocaleResolver localeResolver;

    /**
     * {@inheritDoc}
     */
    @Override
    public void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException exception) throws IOException, ServletException {
        setDefaultFailureUrl("/login?error=true");

        super.onAuthenticationFailure(request, response, exception);

        final Locale locale = localeResolver.resolveLocale(request);

        String errorMessage = messages.getMessage("auth.message.bad-credentials", null, locale);

        if (exception.getMessage().equalsIgnoreCase("User is disabled")) {
            errorMessage = messages.getMessage("auth.message.disabled", null, locale);
        } else if (exception.getMessage().equalsIgnoreCase("User account has expired")) {
            errorMessage = messages.getMessage("auth.message.expired", null, locale);
        } else if (exception.getMessage().equalsIgnoreCase("blocked")) {
            errorMessage = messages.getMessage("auth.message.blocked", null, locale);
        }

        request.getSession().setAttribute("authError", errorMessage);
    }
}
