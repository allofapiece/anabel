package com.pinwheel.anabel.integration;

import com.pinwheel.anabel.entity.Status;
import com.pinwheel.anabel.entity.User;
import com.pinwheel.anabel.external.category.Integration;
import com.pinwheel.anabel.repository.UserRepository;
import com.pinwheel.anabel.service.SiteSettingService;
import com.pinwheel.anabel.service.UserService;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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
@WithUserDetails(value = "mike@gmail.com", userDetailsServiceBeanName = "userService")
public class ProfileSettingsIntegrationTest {
    @Autowired
    private WebApplicationContext context;

    @MockBean
    private SiteSettingService siteSettingService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @Test
    @Sql(value = {"/db/fixture/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/db/fixture/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldRejectSlugUpdatingIfAddressIsAlreadyTaken() throws Exception {
        String slugFormXPath = "//*[@id=\"app\"]//*[@action=\"/user/settings/slug\"]";

        final MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("slug", "mike");

        this.mockMvc.perform(post("/user/settings/slug").params(param).with(csrf()))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(xpath(slugFormXPath + "//*[@name=\"slug\" and contains(@class, 'is-invalid')]").exists())
                .andExpect(xpath(slugFormXPath + "//*[contains(@class, 'uq-err-container') and contains(@class, 'invalid-feedback')]").exists())
                .andExpect(xpath(slugFormXPath + "//*[contains(@class, 'uq-err-container')]//*[@id='slug-error']").string(is("This address is already taken.")));
    }

    @Test
    @Sql(value = {"/db/fixture/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/db/fixture/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldUpdateSlug() throws Exception {
        Mockito.doReturn(List.of("admin")).when(siteSettingService).getValue("slugTakenKeywords");

        String slugFormXPath = "//*[@id=\"app\"]//*[@action=\"/user/settings/slug\"]";

        final MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("slug", "newslug");

        this.mockMvc.perform(post("/user/settings/slug").params(param).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/settings"))
                .andExpect(flash().attribute("flushMessage", "Profile link has been changed."))
                .andExpect(flash().attribute("flushStatus", "success"));

        this.mockMvc.perform(get("/user/settings"))
                .andDo(print())
                .andExpect(xpath(slugFormXPath + "//*[@name=\"slug\" and not(contains(@class, 'is-invalid'))]").exists())
                .andExpect(xpath(slugFormXPath + "//*[contains(@class, 'uq-err-container')]//*[@id='slug-error']").doesNotExist())
                .andExpect(xpath(slugFormXPath + "//*[@name=\"slug\" and @value=\"newslug\"]").exists());

        User user = userRepository.findBySlug("newslug");

        assertNotNull(user);
        assertEquals("mike@gmail.com", user.getEmail());
    }

    @Test
    @Sql(value = {"/db/fixture/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/db/fixture/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldetSettingsPage() throws Exception {
        String slugFormXPath = "//*[@id=\"app\"]//*[@action=\"/user/settings/slug\"]";

        this.mockMvc.perform(get("/user/settings"))
                .andDo(print())
                .andExpect(xpath("//*[@id=\"app\"]//*[contains(@class, 'breadcrumb-item')][1]").string(is("Mike")))
                .andExpect(xpath(slugFormXPath + "//*[@name=\"slug\" and @value=\"mike\"]").exists());
    }

    @Test
    @Sql(value = {"/db/fixture/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/db/fixture/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Transactional
    public void shouldChangePassword() throws Exception {
        final MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("oldPassword", "Qqqq1111");
        param.add("password", "Wwww1111");
        param.add("confirmedPassword", "Wwww1111");

        this.mockMvc.perform(post("/user/settings/password").params(param).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/settings"))
                .andExpect(flash().attribute("flushMessage", "Password has been changed."))
                .andExpect(flash().attribute("flushStatus", "success"));

        User user = userRepository.findByEmail("mike@gmail.com");

        assertEquals(2, user.getPasswords().size());
        assertEquals(Status.EXPIRED, user.getPasswords().get(1).getStatus());
        assertNotEquals(user.getPasswords().get(0).getValue(), user.getPasswords().get(1).getValue());
    }

    @Test
    @Sql(value = {"/db/fixture/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/db/fixture/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Transactional
    public void shouldRejectPasswordChangingIfPasswordHasBeenUsed() throws Exception {
        String slugFormXPath = "//*[@id=\"app\"]//*[@action=\"/user/settings/password\"]";

        User user = userRepository.findByEmail("mike@gmail.com");
        userService.setPasswordForUser(user, "Wwww1111");
        userRepository.save(user);

        final MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("oldPassword", "Wwww1111");
        param.add("password", "Qqqq1111");
        param.add("confirmedPassword", "Qqqq1111");

        this.mockMvc.perform(post("/user/settings/password").params(param).with(csrf()))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(xpath(slugFormXPath + "//*[@name=\"password\" and contains(@class, 'is-invalid')]").exists())
                .andExpect(xpath(slugFormXPath + "//*[@name=\"password\"]/..//div[contains(@class, 'invalid-feedback')]/span[1]").string(containsString("This password has been used and changed")));
    }

    @Test
    @Sql(value = {"/db/fixture/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/db/fixture/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldRejectPasswordChangingIfPasswordsDontMatch() throws Exception {
        String slugFormXPath = "//*[@id=\"app\"]//*[@action=\"/user/settings/password\"]";

        final MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("oldPassword", "Wwww1111");
        param.add("password", "Qqqq1111");
        param.add("confirmedPassword", "Wwww1111");

        this.mockMvc.perform(post("/user/settings/password").params(param).with(csrf()))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(xpath(slugFormXPath + "//*[@name=\"confirmedPassword\" and contains(@class, 'is-invalid')]").exists())
                .andExpect(xpath(slugFormXPath + "//*[@name=\"confirmedPassword\"]/..//div[contains(@class, 'invalid-feedback')]/span[1]").string(is("Passwords do not match.")));
    }

    @Test
    @Sql(value = {"/db/fixture/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/db/fixture/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldRejectPasswordChangingIfPasswordLikeCurrent() throws Exception {
        String slugFormXPath = "//*[@id=\"app\"]//*[@action=\"/user/settings/password\"]";

        final MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("oldPassword", "Qqqq1111");
        param.add("password", "Qqqq1111");
        param.add("confirmedPassword", "Qqqq1111");

        this.mockMvc.perform(post("/user/settings/password").params(param).with(csrf()))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(xpath(slugFormXPath + "//*[@name=\"password\" and contains(@class, 'is-invalid')]").exists())
                .andExpect(xpath(slugFormXPath + "//*[@name=\"password\"]/..//div[contains(@class, 'invalid-feedback')]/span[1]").string(containsString("You use this password already. Please, enter another.")));
    }

    @Test
    @Sql(value = {"/db/fixture/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/db/fixture/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldRejectPasswordChangingIfPasswordIsBlank() throws Exception {
        String slugFormXPath = "//*[@id=\"app\"]//*[@action=\"/user/settings/password\"]";

        this.mockMvc.perform(post("/user/settings/password").params(new LinkedMultiValueMap<>()).with(csrf()))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(xpath(slugFormXPath + "//*[@name=\"oldPassword\" and contains(@class, 'is-invalid')]").exists())
                .andExpect(xpath(slugFormXPath + "//*[@name=\"oldPassword\"]/..//div[contains(@class, 'invalid-feedback')]/span[1]").string(containsString("Must not be blank.")))
                .andExpect(xpath(slugFormXPath + "//*[@name=\"password\" and contains(@class, 'is-invalid')]").exists())
                .andExpect(xpath(slugFormXPath + "//*[@name=\"password\"]/..//div[contains(@class, 'invalid-feedback')]/span[1]").string(containsString("Must not be blank.")))
                .andExpect(xpath(slugFormXPath + "//*[@name=\"confirmedPassword\" and contains(@class, 'is-invalid')]").exists())
                .andExpect(xpath(slugFormXPath + "//*[@name=\"confirmedPassword\"]/..//div[contains(@class, 'invalid-feedback')]/span[1]").string(containsString("Must not be blank.")));
    }
}
