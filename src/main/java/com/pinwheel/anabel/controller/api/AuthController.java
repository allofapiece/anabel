package com.pinwheel.anabel.controller.api;

import com.fasterxml.jackson.annotation.JsonView;
import com.pinwheel.anabel.controller.response.ExtendedResponse;
import com.pinwheel.anabel.entity.User;
import com.pinwheel.anabel.entity.Views;
import com.pinwheel.anabel.entity.dto.UserDto;
import com.pinwheel.anabel.service.UserService;
import com.pinwheel.anabel.service.notification.domain.Alert;
import com.pinwheel.anabel.service.notification.domain.FlushStatus;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Locale;
import java.util.Map;


@RestController("oauthApiController")
@RequiredArgsConstructor
public class AuthController {
    private final ConsumerTokenServices tokenServices;

    /**
     * Injection of the user service.
     */
    private final UserService userService;

    /**
     * Injection of {@link ModelMapper} bean.
     */
    private final ModelMapper modelMapper;

    /**
     * Injection of {@link MessageSource} bean.
     */
    private final MessageSource messageSource;

    @RequestMapping(method = RequestMethod.POST, value = "/oauth/revoke/{token:[a-zA-Z\\d\\-]+}", produces = "application/json; charset=UTF-8")
    public Map revokeToken(@PathVariable String token) {
        tokenServices.revokeToken(token);

        return Map.of("token", token);
    }

    @RequestMapping(value = "api/action/signup", method = RequestMethod.POST, consumes = "application/json; charset=UTF-8")
    @JsonView(Views.WithGeneral.class)
    public ExtendedResponse<User> signup(@Valid @RequestBody UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        user.getPasswords().get(0).setValue(userDto.getPassword());

        return new ExtendedResponse<>(
                this.userService.createUser(user),
                new Alert(messageSource.getMessage(
                        "auth.register.activation.verify",
                        null,
                        LocaleContextHolder.getLocale()
                ), "auth.register.activation.verify", "alert.signup.verify", FlushStatus.SUCCESS)
        );
    }

    @PostMapping(value = "api/action/reactivate", consumes = "application/json; charset=UTF-8")
    public ExtendedResponse<String> reactivate(@RequestBody Map<String, String> map, Locale locale) {
        if (userService.resendActivation(map.get("token"))) {
            return new ExtendedResponse<>(
                    "success",
                    new Alert(messageSource.getMessage("auth.register.activation.reverified", null, locale),
                            "auth.register.activation.reverified", "alert.signup.reverified", FlushStatus.SUCCESS
                    )
            );
        } else {
            return new ExtendedResponse<>(
                    "error",
                    new Alert(messageSource.getMessage("auth.register.activation.reverified.error", null, locale),
                            "auth.register.activation.reverified.error", "alert.signup.reverified.error", FlushStatus.ERROR
                    )
            );
        }
    }
}
