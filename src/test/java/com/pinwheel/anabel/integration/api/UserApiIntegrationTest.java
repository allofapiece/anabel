package com.pinwheel.anabel.integration.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pinwheel.anabel.external.category.Integration;
import com.pinwheel.anabel.external.util.Oauth2Utils;
import com.pinwheel.anabel.repository.UserRepository;
import com.pinwheel.anabel.service.SiteSettingService;
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

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = {"/application-test.properties", "/application-test-local.properties"})
@Category(Integration.class)
class UserApiIntegrationTest {
    @Autowired
    private WebApplicationContext context;
    @MockBean
    private SiteSettingService siteSettingService;

    @Autowired
    private UserRepository userRepository;

    private MockMvc mockMvc;


    private static final ObjectMapper om = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @Test
    @Sql(value = {"/db/fixture/create-user-before.sql", "/db/fixture/create-oauth-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/db/fixture/create-user-after.sql", "/db/fixture/create-oauth-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldRejectPatchingUser() throws Exception {
        Mockito.doReturn(List.of("admin")).when(siteSettingService).getValue("slugTakenKeywords");
        var token = Oauth2Utils.obtainAccessToken(mockMvc);

        mockMvc.perform(patch("/api/users/1").header("Authorization", "Bearer " + token)
                .content(om.writeValueAsString(Map.of("email", "john@gmail.com"))))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.[0].message", is("This email already taken.")))
                .andExpect(jsonPath("$.[0].type", is("TYPE")));
    }

    @Test
    @Sql(value = {"/db/fixture/create-user-before.sql", "/db/fixture/create-oauth-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/db/fixture/create-user-after.sql", "/db/fixture/create-oauth-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldPatchUser() throws Exception {
        Mockito.doReturn(List.of("admin")).when(siteSettingService).getValue("slugTakenKeywords");
        var token = Oauth2Utils.obtainAccessToken(mockMvc);

        mockMvc.perform(patch("/api/users/1").header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(om.writeValueAsString(Map.of("slug", "newslug"))))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertNotNull(userRepository.findBySlug("newslug"));
    }

    @Test
    @Sql(value = {"/db/fixture/create-user-before.sql", "/db/fixture/create-oauth-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/db/fixture/create-user-after.sql", "/db/fixture/create-oauth-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldGetCurrentUser() throws Exception {
        var token = Oauth2Utils.obtainAccessToken(mockMvc);

        mockMvc.perform(get("/api/users/current").header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.displayName", is("Mike")))
                .andExpect(jsonPath("$.createdAt").doesNotExist())
                .andExpect(jsonPath("$.updatedAt").doesNotExist());
    }

    @Test
    @Sql(value = {"/db/fixture/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/db/fixture/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldGetUserBySlug() throws Exception {
        mockMvc.perform(get("/api/users/mike"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.displayName", is("Mike")))
                .andExpect(jsonPath("$.createdAt").doesNotExist())
                .andExpect(jsonPath("$.updatedAt").doesNotExist());
    }

    @Test
    @Sql(value = {"/db/fixture/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/db/fixture/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldGetUserById() throws Exception {
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.displayName", is("Mike")))
                .andExpect(jsonPath("$.createdAt").doesNotExist())
                .andExpect(jsonPath("$.updatedAt").doesNotExist());
    }

    @Test
    @Sql(value = {"/db/fixture/create-user-before.sql", "/db/fixture/create-oauth-before.sql",
            "/db/fixture/create-site-settings-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/db/fixture/create-user-after.sql", "/db/fixture/create-oauth-after.sql",
            "/db/fixture/delete-site-setting.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shoulVerifySlugAndAccept() throws Exception {
        var token = Oauth2Utils.obtainAccessToken(mockMvc);

        mockMvc.perform(post("/api/action/verify/slug")
                .header("Authorization", "Bearer " + token)
                .content(om.writeValueAsString(Map.of("value", "nik@gmail.com")))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8)
                .accept("application/json;charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$", is(true)));
    }

    @Test
    @Sql(value = {"/db/fixture/create-user-before.sql", "/db/fixture/create-oauth-before.sql",
            "/db/fixture/create-site-settings-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/db/fixture/create-user-after.sql", "/db/fixture/create-oauth-after.sql",
            "/db/fixture/delete-site-setting.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldVerifySlugAndReject() throws Exception {
        var token = Oauth2Utils.obtainAccessToken(mockMvc);

        mockMvc.perform(post("/api/action/verify/slug")
                .header("Authorization", "Bearer " + token)
                .content(om.writeValueAsString(Map.of("value", "mike")))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8)
                .accept("application/json;charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$", is(false)));

        mockMvc.perform(post("/api/action/verify/slug")
                .header("Authorization", "Bearer " + token)
                .content(om.writeValueAsString(Map.of("value", "slug1")))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8)
                .accept("application/json;charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$", is(false)));
    }

    @Test
    @Sql(value = {"/db/fixture/create-user-before.sql", "/db/fixture/create-oauth-before.sql",
            "/db/fixture/create-site-settings-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/db/fixture/create-user-after.sql", "/db/fixture/create-oauth-after.sql",
            "/db/fixture/delete-site-setting.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldTakeAddress() throws Exception {
        var token = Oauth2Utils.obtainAccessToken(mockMvc);

        mockMvc.perform(post("/api/action/verify/slug")
                .header("Authorization", "Bearer " + token)
                .content(om.writeValueAsString(Map.of("value", "mike")))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8)
                .accept("application/json;charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$", is(false)));

        mockMvc.perform(post("/api/action/verify/slug")
                .header("Authorization", "Bearer " + token)
                .content(om.writeValueAsString(Map.of("value", "slug1")))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8)
                .accept("application/json;charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$", is(false)));
    }
}
