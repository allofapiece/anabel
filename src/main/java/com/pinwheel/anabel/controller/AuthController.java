package com.pinwheel.anabel.controller;

import com.pinwheel.anabel.entity.User;
import com.pinwheel.anabel.entity.VerificationToken;
import com.pinwheel.anabel.entity.dto.UserDto;
import com.pinwheel.anabel.service.UserService;
import com.pinwheel.anabel.service.notification.FlushNotificationMessage;
import com.pinwheel.anabel.service.notification.FlushStatus;
import com.pinwheel.anabel.service.notification.Notification;
import com.pinwheel.anabel.service.notification.NotificationService;
import com.pinwheel.anabel.util.ConvertUtils;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

/**
 * Describes logic of authentication, registration and etc. Generalizes logic of application security.
 *
 * @author Listratenko Stanislav
 * @version 1.0.0
 */
@RequiredArgsConstructor
@Controller
public class AuthController {
    /**
     * Injection of the user service.
     */
    private final UserService userService;

    private final ModelMapper modelMapper;

    /**
     * Injection of {@link NotificationService} bean.
     */
    private final NotificationService notificationService;

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
            @Valid UserDto userDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model,
            HttpServletRequest request
    ) {
        boolean isConfirmEmpty = StringUtils.isEmpty(confirmedPassword);

        if (isConfirmEmpty) {
            model.addAttribute("confirmedPasswordError", "Password confirmation cannot be empty");
        }

        if (userDto.getPassword() != null && !userDto.getPassword().equals(confirmedPassword)) {
            model.addAttribute("passwordError", "Passwords are different!");
        }

        if (isConfirmEmpty || bindingResult.hasErrors()) {
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
                        FlushNotificationMessage.builder()
                                .status(FlushStatus.SUCCESS)
                                .redirectAttributes(redirectAttributes)
                                .message("Verify your email for completing registration.")
                                .build()
                ).build());

        return "redirect:/login";
    }

    /**
     * Delegates verification code to the user service. Checks verification code in database and whether it is not
     * expired. If verification code is valid set user status as active.
     *
     * @param model Model for saving data to displaying in templates.
     * @param token Verification code from email for checking user.
     * @return Login page.
     */
    @GetMapping("/activate/{token}")
    public String activate(
            Model model,
            @PathVariable String token,
            RedirectAttributes redirectAttributes
    ) {
        boolean isActivated = this.userService.activateUser(token);

        StringBuilder messageBuilder = new StringBuilder("auth.register.activation.");
        String message = isActivated
                ? messageBuilder.append("success").toString()
                : messageBuilder.append("expired").toString();

        notificationService.send(Notification.builder()
                .put(
                        "flush",
                        new User(),
                        FlushNotificationMessage.builder()
                                .status(FlushStatus.SUCCESS)
                                .redirectAttributes(redirectAttributes)
                                .message(message)
                                .build()
                ).build());

        return "redirect:/login";
    }
}
