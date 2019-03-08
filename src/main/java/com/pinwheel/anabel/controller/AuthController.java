package com.pinwheel.anabel.controller;

import com.pinwheel.anabel.entity.User;
import com.pinwheel.anabel.service.UserService;
import com.pinwheel.anabel.util.ConvertUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    /**
     * Returns template of registration page.
     *
     * @return registration page path.
     */
    @GetMapping("/signup")
    public String signup() {
        return "auth/signup";
    }

    /**
     * Checks data of the user and saves him in database if data is valid. If data has errors, errors will be returned
     * to user back. Delegates data of the user to the user service for following processing.
     *
     * @param confirmedPassword Confirmed password. Must be the same with user password.
     * @param user User entity.
     * @param bindingResult Has data of the validation.
     * @param model Model for saving data to displaying in templates.
     * @return Signup page path if registration has errors. Returns redirect to the profile page otherwise.
     */
    @PostMapping("/signup")
    public String signup(
            @RequestParam String confirmedPassword,
            @Valid User user,
            BindingResult bindingResult,
            Model model
    ) {
        boolean isConfirmEmpty = StringUtils.isEmpty(confirmedPassword);

        if (isConfirmEmpty) {
            model.addAttribute("confirmedPasswordError", "Password confirmation cannot be empty");
        }

        if (user.getPassword() != null && !user.getPassword().equals(confirmedPassword)) {
            model.addAttribute("passwordError", "Passwords are different!");
        }

        if (isConfirmEmpty || bindingResult.hasErrors()) {
            Map<String, String> errors = ConvertUtils.getErrors(bindingResult);

            model.mergeAttributes(errors);

            return "auth/signup";
        }

        if (!this.userService.addUser(user)) {
            model.addAttribute("emailError", "That email already exists.");
            return "auth/signup";
        }

        return "redirect:/profile";
    }

    /**
     * Delegates verification code to the user service. Checks verification code in database and whether it is not
     * expired. If verification code is valid set user status as active.
     *
     * @param model Model for saving data to displaying in templates.
     * @param code Verification code from email for checking user.
     * @return Login page.
     */
    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = this.userService.activateUser(code);

        if (isActivated) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "User successfully activated");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Activation code is not found!");
        }

        return "login";
    }
}
