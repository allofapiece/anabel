package com.pinwheel.anabel.service;

import com.pinwheel.anabel.entity.Role;
import com.pinwheel.anabel.entity.Status;
import com.pinwheel.anabel.entity.User;
import com.pinwheel.anabel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.UUID;

/**
 * User service. Provides logic for users.
 *
 * @author Listratenko Stanislav
 * @version 1.0.0
 */
@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    /**
     * Current server address.
     */
    @Value("${server.address}")
    private String serverAddress;

    /**
     * Injection of user repository.
     */
    private final UserRepository userRepository;

    /**
     * Injection of password encoder.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Injection of mail sender.
     */
    private final MailSender mailSender;

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(s);

        if (user == null) {
            throw new UsernameNotFoundException("User not found.");
        }

        return user;
    }

    /**
     * Adds user to database. If provided user email already exists, false value will be returned. Sends verification
     * message.
     *
     * @param user User for saving in database.
     * @return true or false, denotes whether user has been saved.
     */
    public boolean addUser(User user) {
        User userFromDb = userRepository.findByEmail(user.getEmail());

        if (userFromDb != null) {
            return false;
        }

        user.setStatus(Status.ACTIVE);
        user.setRoles(Collections.singleton(Role.USER));
        user.setConfirmationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        sendMessage(user);

        return true;
    }

    /**
     * Sends verification message.
     *
     * @param user User, for which verification message will be sent.
     */
    private void sendMessage(User user) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to Sweater. Please, visit next link: http://%s/activate/%s",
                    user.getUsername(),
                    serverAddress,
                    user.getConfirmationCode()
            );

            mailSender.send(user.getEmail(), "Activation code", message);
        }
    }

    /**
     * Activates user by verification code.
     *
     * @param code Verification code.
     * @return true or false, denotes whether user has been activated.
     */
    public boolean activateUser(String code) {
        User user = userRepository.findByConfirmationCode(code);

        if (user == null) {
            return false;
        }

        user.setConfirmationCode(null);

        userRepository.save(user);

        return true;
    }

}
