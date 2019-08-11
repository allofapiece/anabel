package com.pinwheel.anabel.integration;

import com.pinwheel.anabel.entity.User;
import com.pinwheel.anabel.external.category.Integration;
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
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
                .andExpect(flash().attribute("flushStatus", "success"));;

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
    public void getSettingsPage() throws Exception {
        String slugFormXPath = "//*[@id=\"app\"]//*[@action=\"/user/settings/slug\"]";

        this.mockMvc.perform(get("/user/settings"))
                .andDo(print())
                .andExpect(xpath("//*[@id=\"app\"]//*[contains(@class, 'breadcrumb-item')][1]").string(is("Mike")))
                .andExpect(xpath(slugFormXPath + "//*[@name=\"slug\" and @value=\"mike\"]").exists());
    }
}
