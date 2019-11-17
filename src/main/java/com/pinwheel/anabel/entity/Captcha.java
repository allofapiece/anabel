package com.pinwheel.anabel.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pinwheel.anabel.service.validation.ValidCaptcha;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@ValidCaptcha
@Data
public class Captcha {
    @JsonProperty(value = "g-recaptcha-response")
    private String gRecaptchaResponse;
}
