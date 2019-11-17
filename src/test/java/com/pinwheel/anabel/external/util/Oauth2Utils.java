package com.pinwheel.anabel.external.util;

import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


public class Oauth2Utils {
    private static final String USERNAME = "mike@gmail.com";
    private static final String PASSWORD = "Qqqq1111";
    private static final String CLIENT = "client";
    private static final String SECRET = "Qqqq1111";

    public static String obtainAccessToken(MockMvc mockMvc) throws Exception {
        return obtainAccessToken(mockMvc, CLIENT, SECRET);
    }

    public static String obtainAccessToken(MockMvc mockMvc, String client, String secret) throws Exception {
        return obtainAccessToken(mockMvc, client, secret, USERNAME, PASSWORD);
    }

    public static String obtainAccessToken(MockMvc mockMvc, String client, String secret, String username, String password) throws Exception {
        return obtainTokenMap(mockMvc, client, secret, username, password).get("access_token").toString();
    }

    public static String obtainRefreshToken(MockMvc mockMvc) throws Exception {
        return obtainRefreshToken(mockMvc, CLIENT, SECRET);
    }

    public static String obtainRefreshToken(MockMvc mockMvc, String client, String secret) throws Exception {
        return obtainRefreshToken(mockMvc, client, secret, USERNAME, PASSWORD);
    }

    public static String obtainRefreshToken(MockMvc mockMvc, String client, String secret, String username, String password) throws Exception {
        return obtainTokenMap(mockMvc, client, secret, username, password).get("refresh_token").toString();
    }

    public static Map<String, Object> obtainTokenMap(MockMvc mockMvc) throws Exception {
        return obtainTokenMap(mockMvc, CLIENT, SECRET);
    }

    public static Map<String, Object> obtainTokenMap(MockMvc mockMvc, String client, String secret) throws Exception {
        return obtainTokenMap(mockMvc, client, secret, USERNAME, PASSWORD);
    }

    public static Map<String, Object> obtainTokenMap(MockMvc mockMvc, String client, String secret, String username, String password) throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", client);
        params.add("client_secret", secret);
        params.add("username", username);
        params.add("password", password);

        return obtainTokenMap(mockMvc.perform(post("/oauth/token")
                .params(params)
                .accept("application/json;charset=UTF-8")));
    }

    public static Map<String, Object> obtainTokenMap(ResultActions result) throws Exception {
        result.andExpect(jsonPath("$.access_token").exists())
                .andExpect(jsonPath("$.refresh_token").exists());

        return new JacksonJsonParser().parseMap(result.andReturn().getResponse().getContentAsString());
    }
}
