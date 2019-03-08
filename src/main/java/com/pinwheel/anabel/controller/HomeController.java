package com.pinwheel.anabel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
}
