package com.pinwheel.anabel.service;

import com.pinwheel.anabel.entity.Password;
import com.pinwheel.anabel.entity.Status;
import com.pinwheel.anabel.entity.User;
import com.pinwheel.anabel.entity.VerificationToken;
import com.pinwheel.anabel.external.category.Unit;
import com.pinwheel.anabel.repository.UserRepository;
import com.pinwheel.anabel.service.notification.NotificationService;
import com.pinwheel.anabel.service.notification.domain.Notification;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(locations = {
        "/application-test.properties",
        "/application-test-local.properties"
})
@Category(Unit.class)
public class UserServiceUnitTest {

    @Autowired
    private UserService userService;

    @MockBean
    private VerificationTokenService verificationTokenService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private NotificationService notificationService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void shouldSetActivePasswordToExpiredAndAddNewEncodedPassword() {
        Mockito.doReturn("1231234123").when(passwordEncoder).encode("1234");

        User user = new User();
        List<Password> passwords = user.getPasswords();

        passwords.add(Password.builder()
                .id(1L)
                .value("123")
                .status(Status.ACTIVE)
                .build());

        passwords.add(Password.builder()
                .id(2L)
                .value("321")
                .status(Status.EXPIRED)
                .build());

        userService.setPasswordForUser(user, "1234");

        assertEquals(3, passwords.size());
        assertEquals(passwords.get(0).getStatus(), Status.ACTIVE);
        assertEquals(passwords.get(0).getValue(), "1231234123");
        assertEquals(passwords.get(1).getValue(), "123");
        assertEquals(1, passwords.stream().filter(pass -> pass.getStatus().equals(Status.ACTIVE)).count());
    }

    @Test
    public void shouldSetPasswordToLastPassword() {
        Mockito.doReturn("1231234123").when(passwordEncoder).encode("1234");

        User user = new User();
        List<Password> passwords = user.getPasswords();

        passwords.add(Password.builder()
                .value("")
                .status(Status.ACTIVE)
                .build());

        passwords.add(Password.builder()
                .id(2L)
                .value("321")
                .status(Status.EXPIRED)
                .build());

        userService.setPasswordForUser(user, "1234");

        assertEquals(2, passwords.size());
        assertEquals(passwords.get(0).getStatus(), Status.ACTIVE);
        assertEquals(passwords.get(0).getValue(), "1231234123");
        assertEquals(passwords.get(1).getValue(), "321");
        assertEquals(1, passwords.stream().filter(pass -> pass.getStatus().equals(Status.ACTIVE)).count());
    }

    @Test
    public void shouldSetPasswordWithoutPasswordEncoding() {
        User user = new User();
        List<Password> passwords = user.getPasswords();

        userService.setPasswordForUser(user, "1234", false);

        assertEquals(1, passwords.size());
        assertEquals(passwords.get(0).getStatus(), Status.ACTIVE);
        assertEquals(passwords.get(0).getValue(), "1234");

        Mockito.verify(passwordEncoder, Mockito.never()).encode(any(String.class));
    }

    @Test
    public void shouldCreateUserAndSendActivateMail() {
        String userPassword = "1";
        Mockito.doReturn(null).when(userRepository).findByEmail(any(String.class));
        Mockito.doReturn(userPassword).when(passwordEncoder).encode(eq(userPassword));

        User user = new User();
        user.setFirstName("Valid name");
        user.setLastName("Valid last name");
        user.setEmail("valid@email.com");
        user.setPassword(new Password(userPassword));

        Mockito.doReturn(user).when(userRepository).save(eq(user));
        Mockito.doReturn(new VerificationToken()).when(verificationTokenService).create(eq(user), any(String.class));
        Mockito.doReturn(true).when(notificationService).send(any(Notification.class));

        user.addVerificationToken(new VerificationToken("validToken"));
        assertNotNull(userService.createUser(user));

        Mockito.verify(notificationService, Mockito.times(1)).send(any(Notification.class));
    }

    @Test
    public void shouldReturnNullIfUserAllreadyExists() {
        User user = new User();
        user.setEmail("existent@email.com");

        Mockito.doReturn(user).when(userRepository).findByEmail("existent@email.com");

        assertNull(userService.createUser(user));

        Mockito.verify(notificationService, Mockito.never()).send(any(Notification.class));
    }
}
