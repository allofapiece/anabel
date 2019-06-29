package com.pinwheel.anabel.controller;

import com.pinwheel.anabel.entity.User;
import com.pinwheel.anabel.entity.dto.CaptchaDto;
import com.pinwheel.anabel.entity.dto.UserDto;
import com.pinwheel.anabel.service.CaptchaService;
import com.pinwheel.anabel.service.UserService;
import com.pinwheel.anabel.service.notification.NotificationService;
import com.pinwheel.anabel.service.notification.domain.Notification;
import com.pinwheel.anabel.service.notification.domain.NotificationMessage;
import com.pinwheel.anabel.service.notification.factory.FlushNotificationMessageFactory;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * Describes logic of authentication, registration and etc. Generalizes logic of application security.
 *
 * @version 1.0.0
 */
@RequiredArgsConstructor
@Controller
public class AuthController {
    /**
     * Injection of the user service.
     */
    private final UserService userService;

    /**
     * Inject of {@link CaptchaService} bean.
     */
    private final CaptchaService captchaService;

    /**
     * Injection of {@link ModelMapper} bean.
     */
    private final ModelMapper modelMapper;

    /**
     * Injection of {@link NotificationService} bean.
     */
    private final NotificationService notificationService;

    /**
     * Inject of {@link FlushNotificationMessageFactory} bean.
     */
    private final FlushNotificationMessageFactory flushNotificationMessageFactory;

    /**
     * Returns template of registration page.
     *
     * @return registration page path.
     */
    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "auth/signup";
    }

    /**
     * Checks data of the user and saves him in database if data is valid. If data has errors, errors will be returned
     * to user back. Delegates data of the user to the user service for following processing.
     *
     * @param confirmedPassword Confirmed password. Must be the same with user password.
     * @param userDto           User entity.
     * @param bindingResult     Has data of the validation.
     * @param model             Model for saving data to displaying in templates.
     * @return Signup page path if registration has errors. Returns redirect to the profile page otherwise.
     */
    @PostMapping("/signup")
    public String signup(
            @RequestParam String confirmedPassword,
            @RequestParam("g-recaptcha-response") String captcha,
            @Valid UserDto userDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        CaptchaDto captchaDto = captchaService.verify(captcha);

        if (!captchaDto.isSuccess()) {
            model.addAttribute("captchaError", "auth.message.captcha.empty");
        }

        boolean isConfirmEmpty = StringUtils.isEmpty(confirmedPassword);

        if (isConfirmEmpty) {
            model.addAttribute("confirmedPasswordError", "Password confirmation cannot be empty");
        }

        if (userDto.getPassword() != null && !userDto.getPassword().equals(confirmedPassword)) {
            model.addAttribute("passwordError", "Passwords are different!");
        }

        if (isConfirmEmpty || bindingResult.hasErrors() || !captchaDto.isSuccess()) {
            return "auth/signup";
        }

        User user = modelMapper.map(userDto, User.class);
        user.getPasswords().get(0).setValue(userDto.getPassword());

        if (this.userService.createUser(user) == null) {
            model.addAttribute("emailError", "That email already exists.");
            return "auth/signup";
        }

        notificationService.send(Notification.builder()
                .put(
                        "flush",
                        new User(),
                        flushNotificationMessageFactory.create("verifyYourEmail", redirectAttributes)
                ).build());

        return "redirect:/login";
    }

    /**
     * Delegates verification code to the user service. Checks verification code in database and whether it is not
     * expired. If verification code is valid set user status as active.
     *
     * @param token Verification code from email for checking user.
     * @return Login page.
     */
    @GetMapping("/activate/{token}")
    public String activate(
            @PathVariable String token,
            RedirectAttributes redirectAttributes
    ) {
        boolean isActivated = this.userService.activateUser(token);

        NotificationMessage message = isActivated
                ? flushNotificationMessageFactory.create("successActivation", redirectAttributes)
                : flushNotificationMessageFactory.create("resendVerification", redirectAttributes, token);

        notificationService.send(Notification.builder()
                .put("flush", new User(), message)
                .build());

        return "redirect:/login";
    }

    /**
     * Creates new verification token for user. Old token will be set as expired.
     *
     * @param oldToken           old verification token for identifying user.
     * @param redirectAttributes redirect attributes for sending response to user.
     * @return redirect to login page.
     */
    @GetMapping("/reactivate/{oldToken}")
    public String reactivate(
            @PathVariable String oldToken,
            RedirectAttributes redirectAttributes
    ) {
        userService.resendActivation(oldToken);

        notificationService.send(Notification.builder()
                .put(
                        "flush",
                        new User(),
                        flushNotificationMessageFactory.create("verifyYourEmail", redirectAttributes))
                .build());

        return "redirect:/login";
    }
}
