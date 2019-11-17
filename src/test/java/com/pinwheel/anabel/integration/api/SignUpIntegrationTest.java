package com.pinwheel.anabel.integration.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pinwheel.anabel.entity.Status;
import com.pinwheel.anabel.external.category.Integration;
import com.pinwheel.anabel.service.CaptchaService;
import org.junit.Ignore;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = {"/application-test.properties", "/application-test-local.properties"})
@Category(Integration.class)
class SignUpIntegrationTest {
    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private CaptchaService captchaService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @Test
    @Sql(value = {"/db/fixture/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldSignUpSuccessfully() throws Exception {
        Mockito.doReturn(true).when(captchaService).verify("captcha");

        var map = Map.of(
                "email", "ruddy@gmail.com",
                "firstName", "Ruddy",
                "lastName", "Look",
                "password", "Qqqq1111",
                "confirmedPassword", "Qqqq1111",
                "g-recaptcha-response", "captcha"
        );

        mockMvc.perform(post("/api/action/signup")
                .content(om.writeValueAsString(map))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$._embedded.id", is(1)))
                .andExpect(jsonPath("$._embedded.firstName", is("Ruddy")))
                .andExpect(jsonPath("$._embedded.lastName", is("Look")))
                .andExpect(jsonPath("$._embedded.status", is(Status.PENDING_VERIFICATION.toString())))
                .andExpect(jsonPath("$._embedded.createdAt").doesNotExist())
                .andExpect(jsonPath("$._embedded.updatedAt").doesNotExist())
                .andExpect(jsonPath("$.alerts.[0].message", is("Done. Verify your email for completing registration.")))
                .andExpect(jsonPath("$.alerts.[0].type", is("SUCCESS")));
    }

    @Test
    @Sql(value = {"/db/fixture/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/db/fixture/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldRejectIfEmailExists() throws Exception {
        Mockito.doReturn(true).when(captchaService).verify("captcha");

        var map = Map.of(
                "email", "mike@gmail.com",
                "firstName", "Ruddy",
                "lastName", "Look",
                "password", "Qqqq1111",
                "confirmedPassword", "Qqqq1111",
                "g-recaptcha-response", "captcha"
        );

        mockMvc.perform(post("/api/action/signup")
                .content(om.writeValueAsString(map))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.[0].property", is("email")))
                .andExpect(jsonPath("$.[0].message", is("This email already taken.")))
                .andExpect(jsonPath("$.[0].type", is("FIELD")))
                .andExpect(jsonPath("$.[0].value", is("mike@gmail.com")));
    }

    @Test
    @Sql(value = {"/db/fixture/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Ignore
    void shouldRejectIfCaptchaIsInvalid() throws Exception {
        Mockito.doReturn(true).when(captchaService).verify("captcha");

        var map = Map.of(
                "email", "mike@gmail.com",
                "firstName", "Ruddy",
                "lastName", "Look",
                "password", "Qqqq1111",
                "confirmedPassword", "Qqqq1111",
                "g-recaptcha-response", "notcaptcha"
        );

        mockMvc.perform(post("/api/action/signup")
                .content(om.writeValueAsString(map))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.[0].property", nullValue()))
                .andExpect(jsonPath("$.[0].type", is("TYPE")))
                .andExpect(jsonPath("$.[0].message", is("Invalid captcha.")))
                .andExpect(jsonPath("$.[0].value", nullValue()));
    }

    @Test
    @Sql(value = {"/db/fixture/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldRejectIfValidationFailed() throws Exception {
        Mockito.doReturn(true).when(captchaService).verify("captcha");

        var map = Map.of(
                "email", "",
                "firstName", "",
                "lastName", "",
                "password", "",
                "confirmedPassword", "",
                "g-recaptcha-response", "captcha"
        );

        mockMvc.perform(post("/api/action/signup")
                .content(om.writeValueAsString(map))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$..property", containsInAnyOrder("confirmedPassword", "firstName", "lastName", "password", "email", "email")))
                .andExpect(jsonPath("$.[?(@.property=='email')].[?(@.message =~ /Size must be between.*/i)]").exists())
                .andExpect(jsonPath("$.[?(@.property=='email')].[?(@.message =='Invalid Email')]").exists())
                .andExpect(jsonPath("$.[?(@.property=='password')].[?(@.message =~ /.*Password must have more characters than.*/i)]").exists())
                .andExpect(jsonPath("$.[?(@.property=='password')].[?(@.message =~ /.*Must be one lowercase character.*/i)]").exists())
                .andExpect(jsonPath("$.[?(@.property=='confirmedPassword')].[?(@.message =~ /Size must be between.*/i)]").exists())
                .andExpect(jsonPath("$.[?(@.property=='firstName')].[?(@.message =~ /Size must be between.*/i)]").exists())
                .andExpect(jsonPath("$.[?(@.property=='lastName')].[?(@.message =~ /Size must be between.*/i)]").exists());
    }

    @Test
    @Sql(value = {"/db/fixture/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldRejectIfPasswordsDontMatch() throws Exception {
        var map = Map.of(
                "email", "mike@gmail.com",
                "firstName", "Ruddy",
                "lastName", "Look",
                "password", "Qqqq1111",
                "confirmedPassword", "Qqqq2222"
        );

        mockMvc.perform(post("/api/action/signup")
                .content(om.writeValueAsString(map))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.[0].property", nullValue()))
                .andExpect(jsonPath("$.[0].value", nullValue()))
                .andExpect(jsonPath("$.[0].message", is("Passwords do not match.")))
                .andExpect(jsonPath("$.[0].type", is("TYPE")));
    }

    @Test
    @Sql(value = {"/db/fixture/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldRejectIfPasswordsIsNull() throws Exception {
        var map = Map.of(
                "email", "mike@gmail.com",
                "firstName", "Ruddy",
                "lastName", "Look",
                "confirmedPassword", "Qqqq2222"
        );

        mockMvc.perform(post("/api/action/signup")
                .content(om.writeValueAsString(map))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.[0].property", is("password")))
                .andExpect(jsonPath("$.[0].value", nullValue()))
                .andExpect(jsonPath("$.[0].message", is("Must not be null.")))
                .andExpect(jsonPath("$.[0].type", is("FIELD")));
    }
}
