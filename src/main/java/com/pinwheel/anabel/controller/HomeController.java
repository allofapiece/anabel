package com.pinwheel.anabel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Locale;

/**
 * Home controller. Describes home pages.
 *
 * @author Listratenko Stanislav
 * @version 1.0.0
 */
@Controller
public class HomeController {

    /**
     * Returns home page.
     *
     * @return Home page.
     */
    @GetMapping("/")
    public String home() {
        return "home";
    }

    /**
     * Uses for switching languages. Though spring framework is able to intercept request and analyze necessary locale,
     * Spring MVC and Spring Security is separated layout of framework and default implementation of
     * {@link org.springframework.web.servlet.i18n.LocaleChangeInterceptor LocaleChangeInterceptor} is not able to
     * intercept requests for controllers, which has been added by {@code addViewControllers} method of
     * {@link org.springframework.web.servlet.config.annotation.WebMvcConfigurer WebMvcConfigurer}.
     *
     * TODO find solution for this problem.
     *
     * @param locale current locale for active session.
     * @return current locale.
     */
    @GetMapping("/lang")
    public @ResponseBody
    Locale lang(Locale locale) {
        return locale;
    }
}
