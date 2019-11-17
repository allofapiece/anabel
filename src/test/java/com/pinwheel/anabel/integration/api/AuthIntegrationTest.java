package com.pinwheel.anabel.integration.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pinwheel.anabel.external.category.Integration;
import com.pinwheel.anabel.external.util.Oauth2Utils;
import com.pinwheel.anabel.repository.VerificationTokenRepository;
import com.pinwheel.anabel.service.CaptchaService;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = {"/application-test.properties", "/application-test-local.properties"})
@Category(Integration.class)
class AuthIntegrationTest {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private DefaultTokenServices tokenServices;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    private static final ObjectMapper om = new ObjectMapper();

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @Test
    @Sql(value = {"/db/fixture/create-oauth-before.sql", "/db/fixture/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/db/fixture/create-oauth-after.sql", "/db/fixture/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldGetToken() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", "client");
        params.add("client_secret", "Qqqq1111");
        params.add("username", "mike@gmail.com");
        params.add("password", "Qqqq1111");

        mockMvc.perform(post("/oauth/token")
                .params(params)
                .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.access_token").exists())
                .andExpect(jsonPath("$.token_type", is("bearer")))
                .andExpect(jsonPath("$.scope", is("read write")))
                .andExpect(jsonPath("$.refresh_token").exists());
    }

    @Test
    @Sql(value = {"/db/fixture/create-oauth-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/db/fixture/create-oauth-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldDisplayBadCredentials() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", "client");
        params.add("client_secret", "Qqqq1111");
        params.add("username", "nonexistent@gmail.com");
        params.add("password", "Qqqq1111");

        mockMvc.perform(post("/oauth/token")
                .params(params)
                .accept("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    @Sql(value = {"/db/fixture/create-oauth-before.sql", "/db/fixture/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/db/fixture/create-oauth-after.sql", "/db/fixture/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldRevokeToken() throws Exception {
        String token = Oauth2Utils.obtainAccessToken(mockMvc);

        mockMvc.perform(get("/api/profile/current").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

        mockMvc.perform(post("/oauth/revoke/" + token).header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.token", is(token)));

        mockMvc.perform(get("/api/profile/current").header("Authorization", "Bearer " + token))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(post("/oauth/revoke/" + token).header("Authorization", "Bearer " + token))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error", is("invalid_token")));
    }

    @Test
    @Sql(value = {"/db/fixture/create-oauth-before.sql", "/db/fixture/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/db/fixture/create-oauth-after.sql", "/db/fixture/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldRefreshToken() throws Exception {
        var tokenMap = Oauth2Utils.obtainTokenMap(mockMvc);
        String access = tokenMap.get("access_token").toString();
        String refresh = tokenMap.get("refresh_token").toString();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "refresh_token");
        params.add("refresh_token", refresh);

        ResultActions result = mockMvc.perform(post("/oauth/token")
                .params(params)
                .with(httpBasic("client", "Qqqq1111"))
                .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.access_token").exists())
                .andExpect(jsonPath("$.token_type", is("bearer")))
                .andExpect(jsonPath("$.scope", is("read write")))
                .andExpect(jsonPath("$.refresh_token").exists());

        var newTokenMap = Oauth2Utils.obtainTokenMap(result);
        String newAccess = newTokenMap.get("access_token").toString();
        String newRefresh = newTokenMap.get("refresh_token").toString();

        assertEquals(refresh, newRefresh);
        assertNotEquals(access, newAccess);

        mockMvc.perform(get("/api/profile/current").header("Authorization", "Bearer " + access))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/api/profile/current").header("Authorization", "Bearer " + newAccess))
                .andExpect(status().isOk());
    }

    @Test
    @Sql(value = {"/db/fixture/create-oauth-expired-before.sql", "/db/fixture/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/db/fixture/create-user-after.sql", "/db/fixture/create-oauth-expired-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldRejectExpiredToken() throws Exception {
        tokenServices.setAccessTokenValiditySeconds(1);

        String access = Oauth2Utils.obtainAccessToken(mockMvc);
        Thread.sleep(1000);

        mockMvc.perform(get("/api/profile/current").header("Authorization", "Bearer " + access))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error", is("invalid_token")));
    }

    @Test
    @Sql(value = {"/db/fixture/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/db/fixture/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldResendVerificationToken() throws Exception {
        long initialCount = verificationTokenRepository.count();

        mockMvc.perform(post("/api/action/reactivate")
                .content(om.writeValueAsString(Map.of("token", "token")))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8)
                .accept("application/json;charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.alerts").exists())
                .andExpect(jsonPath("$.alerts[0].code", is("auth.register.activation.reverified")))
                .andExpect(jsonPath("$.alerts[0].type", is("SUCCESS")));

        assertEquals(verificationTokenRepository.count() - initialCount, 1);
    }

    @Test
    @Sql(value = {"/db/fixture/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/db/fixture/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldSendErrorAlertIfTokenIsInvalid() throws Exception {
        mockMvc.perform(post("/api/action/reactivate")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8)
                .content(om.writeValueAsString(Map.of("token", "nonexistent-token")))
                .accept("application/json;charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.alerts").exists())
                .andExpect(jsonPath("$.alerts[0].code", is("auth.register.activation.reverified.error")))
                .andExpect(jsonPath("$.alerts[0].type", is("ERROR")));
    }
}
