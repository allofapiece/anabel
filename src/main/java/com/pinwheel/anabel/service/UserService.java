package com.pinwheel.anabel.service;

import com.pinwheel.anabel.entity.*;
import com.pinwheel.anabel.entity.dto.UserChangePasswordDto;
import com.pinwheel.anabel.event.OnRegistrationCompleteEvent;
import com.pinwheel.anabel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * User service. Provides logic for users.
 *
 * @version 1.0.0
 */
@RequiredArgsConstructor
@Service
@Transactional
public class UserService implements UserDetailsService {
    /**
     * Injection of user repository.
     */
    private final UserRepository userRepository;

    /**
     * Injection of password encoder.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Injection of {@link VerificationTokenService} bean.
     */
    private final VerificationTokenService verificationTokenService;

    /**
     * Injection of {@link ApplicationEventPublisher} bean.
     */
    private final ApplicationEventPublisher eventPublisher;

    private final SlugGenerator slugGenerator;

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(s);

        if (user == null) {
            throw new UsernameNotFoundException("User not found.");
        }

        Hibernate.initialize(user.getPasswords());

        return user;
    }

    /**
     * Adds user to database. If provided user email already exists, false value will be returned. Sends verification
     * message.
     *
     * @param user User for saving in database.
     * @return true or false, denotes whether user has been saved.
     */
    public User createUser(User user) {
        User userFromDb = userRepository.findByEmail(user.getEmail());

        if (userFromDb != null) {
            return null;
        }

        user.setStatus(Status.PENDING_VERIFICATION);
        user.setRoles(Collections.singleton(Role.USER));
        user.setSlug(slugGenerator.slug(
                user.getDisplayName(),
                userRepository::findSlugsBySlugRegexp
        ));

        this.setPasswordForUser(user, user.getPassword());

        user = userRepository.save(user);

        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user));

        return user;
    }

    /**
     * Activates user by verification code.
     *
     * @param token Verification code.
     * @return true or false, denotes whether user has been activated.
     */
    public boolean activateUser(String token) {
        VerificationToken verificationToken = verificationTokenService.findByToken(token);
        User user = verificationToken.getUser();

        if (user == null) {
            return false;
        }

        if (verificationTokenService.isExpired(verificationToken)) {
            return false;
        }

        verificationTokenService.reject(verificationToken);
        user.setStatus(Status.ACTIVE);

        userRepository.save(user);

        return true;
    }

    public boolean resendActivation(String oldToken) {
        VerificationToken verificationToken = verificationTokenService.findByToken(oldToken);

        if (verificationToken == null) {
            return false;
        }

        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(verificationToken.getUser()));

        return true;
    }

    public boolean changePassword(User user, UserChangePasswordDto dto, BindingResult bindingResult) {
        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
            bindingResult.rejectValue("oldPassword", "form.user.password.error.old");
            return false;
        }

        if (passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            bindingResult.rejectValue("password", "form.user.password.error.current");
            return false;
        }

        Optional<Password> usedPassword = user.getPasswords()
                .stream()
                .filter(password -> password.getStatus().equals(Status.EXPIRED))
                .filter(password -> passwordEncoder.matches(dto.getPassword(), password.getValue()))
                .findFirst();

        if (usedPassword.isPresent()) {
            bindingResult.rejectValue("password", "form.user.password.error.used",
                    new Object[]{usedPassword.get().getUpdatedAt()}, "This password has been used.");
            return false;
        }

        setPasswordForUser(user, dto.getPassword());

        userRepository.save(user);

        return true;
    }

    /**
     * Sets new password for user. Determines whether need to use password encoder by passed {@code useEncoder}
     * parameter.
     *
     * @param user       target user.
     * @param value      password value.
     * @param useEncoder determines whether need to use password encoder.
     */
    public void setPasswordForUser(User user, String value, boolean useEncoder) {
        value = useEncoder ? passwordEncoder.encode(value) : value;

        List<Password> passwords = user.getPasswords();
        Password password;

        if (!passwords.isEmpty() && passwords.get(0).getId() == null) {
            password = passwords.get(0);
            password.setValue(value);

            if (passwords.size() > 1) {
                setPasswordsAsExpired(passwords.subList(1, passwords.size() - 1));
            }
        } else {
            password = Password.builder()
                    .value(value)
                    .status(Status.ACTIVE)
                    .build();

            if (password.getStatus().equals(Status.ACTIVE)) {
                setPasswordsAsExpired(passwords);
            }

            passwords.add(0, password);
        }

        password.setUser(user);
    }

    /**
     * Sets new password for user. Uses password encoder by default.
     *
     * @param user  target user.
     * @param value password value.
     */
    public void setPasswordForUser(User user, String value) {
        this.setPasswordForUser(user, value, true);
    }

    /**
     * Sets old passwords as expired. Determines active passwords and sets them as expired.
     *
     * @param passwords list of passwords.
     */
    public void setPasswordsAsExpired(List<Password> passwords) {
        passwords.stream()
                .filter(pass -> pass.getStatus().equals(Status.ACTIVE))
                .findAny()
                .ifPresent(activePassword -> activePassword.setStatus(Status.EXPIRED));
    }
}
