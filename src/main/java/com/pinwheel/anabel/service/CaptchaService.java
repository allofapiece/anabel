package com.pinwheel.anabel.service;

import com.pinwheel.anabel.entity.dto.CaptchaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * Captcha service. Contains logic for google captcha.
 *
 * @version 1.0.0
 */
@RequiredArgsConstructor
@Service
public class CaptchaService {
    /**
     * Inject of {@link RestTemplate} bean.
     */
    private final RestTemplate restTemplate;

    /**
     * Url for processing captcha.
     */
    private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    /**
     * Captcha secret.
     */
    @Value("${application.recaptcha.secret}")
    private String secret;

    /**
     * Verifies whether captcha is valid.
     *
     * @param captcha captcha string.
     * @return captcha dto, that contains captcha status.
     */
    public CaptchaDto verify(String captcha) {
        String url = String.format(CAPTCHA_URL, secret, captcha);
        CaptchaDto response = restTemplate.postForObject(url, Collections.emptyList(), CaptchaDto.class);

        return response;
    }
}
