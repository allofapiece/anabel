package com.pinwheel.anabel.service;

import com.pinwheel.anabel.entity.Password;
import com.pinwheel.anabel.entity.Status;
import com.pinwheel.anabel.entity.User;
import com.pinwheel.anabel.entity.VerificationToken;
import com.pinwheel.anabel.entity.dto.UserChangePasswordDto;
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
import org.springframework.validation.BeanPropertyBindingResult;

import java.sql.Timestamp;
import java.util.Date;
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

    @MockBean
    private BeanPropertyBindingResult bindingResult;

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
        user.setDisplayName("Display Name");
        user.setEmail("valid@email.com");
        user.setPassword(new Password(userPassword));

        Mockito.doReturn(user).when(userRepository).save(eq(user));
        Mockito.doReturn(new VerificationToken()).when(verificationTokenService).create(eq(user), any(String.class));
        Mockito.doNothing().when(notificationService).sendAsync(any(Notification.class));

        user.addVerificationToken(new VerificationToken("validToken"));
        assertNotNull(userService.createUser(user));
        assertEquals("valid-name-valid-last-name", user.getSlug());

        Mockito.verify(notificationService, Mockito.times(1)).sendAsync(any(Notification.class));
    }

    @Test
    public void shouldChangePassword() {
        User user = new User();
        user.setEmail("mike@gmail.com");
        user.setPassword(new Password(1L, "qqqq"));

        var dto = new UserChangePasswordDto();
        dto.setOldPassword("qqqq");
        dto.setPassword("wwww");
        dto.setConfirmedPassword("wwww");

        Mockito.doReturn("qqqq").when(passwordEncoder).encode("qqqq");
        Mockito.doReturn(true).when(passwordEncoder).matches("qqqq", "qqqq");
        Mockito.doReturn("wwww").when(passwordEncoder).encode("wwww");
        Mockito.doReturn(user).when(userRepository).save(eq(user));

        assertTrue(userService.changePassword(user, dto, bindingResult));
        assertTrue(bindingResult.getAllErrors().isEmpty());
        assertEquals("wwww", user.getPassword());
        assertEquals(2, user.getPasswords().size());
        assertEquals(Status.EXPIRED, user.getPasswords().get(1).getStatus());

        Mockito.verify(userRepository, Mockito.times(1)).save(any(User.class));
    }

    @Test
    public void shouldRejectPasswordChangingIfOldPasswordIncorrect() {
        User user = new User();
        user.setEmail("mike@gmail.com");
        user.setPassword(new Password(1L, "qqqq"));

        var dto = new UserChangePasswordDto();
        dto.setOldPassword("eeee");
        dto.setPassword("wwww");
        dto.setConfirmedPassword("wwww");

        Mockito.doReturn("qqqq").when(passwordEncoder).encode("qqqq");
        Mockito.doReturn("wwww").when(passwordEncoder).encode("wwww");
        Mockito.doNothing().when(bindingResult).rejectValue(any(String.class), any(String.class));

        assertFalse(userService.changePassword(user, dto, bindingResult));
        assertEquals("qqqq", user.getPassword());
        assertEquals(1, user.getPasswords().size());
        assertEquals(Status.ACTIVE, user.getPasswords().get(0).getStatus());

        Mockito.verify(userRepository, Mockito.never()).save(any(User.class));
        Mockito.verify(bindingResult, Mockito.times(1))
                .rejectValue("oldPassword", "form.user.password.error.old");
    }

    @Test
    public void shouldRejectPasswordChangingIfPasswordHasBeenUsed() {
        User user = new User();
        user.setEmail("mike@gmail.com");
        user.setPasswords(List.of(
                Password.builder()
                        .value("wwww")
                        .createdAt(new Timestamp(new Date().getTime()))
                        .status(Status.ACTIVE)
                        .build(),
                Password.builder()
                        .value("qqqq")
                        .updatedAt(new Timestamp(new Date().getTime()))
                        .status(Status.EXPIRED)
                        .build()
        ));

        var dto = new UserChangePasswordDto();
        dto.setOldPassword("wwww");
        dto.setPassword("qqqq");
        dto.setConfirmedPassword("qqqq");

        Mockito.doReturn(true).when(passwordEncoder).matches("qqqq", "qqqq");
        Mockito.doReturn(true).when(passwordEncoder).matches("wwww", "wwww");
        Mockito.doNothing().when(bindingResult).rejectValue(any(String.class), any(String.class),
                any(Object[].class), eq("This password has been used."));

        assertFalse(userService.changePassword(user, dto, bindingResult));
        assertEquals("wwww", user.getPassword());
        assertEquals(2, user.getPasswords().size());
        assertEquals(Status.ACTIVE, user.getPasswords().get(0).getStatus());

        Mockito.verify(userRepository, Mockito.never()).save(any(User.class));
        Mockito.verify(bindingResult, Mockito.times(1))
                .rejectValue(eq("password"), eq("form.user.password.error.used"),
                        any(Object[].class), eq("This password has been used."));
    }

    @Test
    public void shouldRejectIfPasswordLikeCurrent() {
        User user = new User();
        user.setEmail("mike@gmail.com");
        user.setPasswords(List.of(
                Password.builder()
                        .value("qqqq")
                        .createdAt(new Timestamp(new Date().getTime()))
                        .status(Status.ACTIVE)
                        .build()
        ));

        var dto = new UserChangePasswordDto();
        dto.setOldPassword("qqqq");
        dto.setPassword("qqqq");
        dto.setConfirmedPassword("qqqq");

        Mockito.doReturn(true).when(passwordEncoder).matches("qqqq", "qqqq");
        Mockito.doNothing().when(bindingResult).rejectValue(any(String.class), any(String.class));

        assertFalse(userService.changePassword(user, dto, bindingResult));
        assertEquals("qqqq", user.getPassword());
        assertEquals(1, user.getPasswords().size());
        assertEquals(Status.ACTIVE, user.getPasswords().get(0).getStatus());

        Mockito.verify(userRepository, Mockito.never()).save(any(User.class));
        Mockito.verify(bindingResult, Mockito.times(1))
                .rejectValue("password", "form.user.password.error.current");
    }
}
