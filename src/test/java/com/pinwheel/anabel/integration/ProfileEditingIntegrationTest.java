package com.pinwheel.anabel.integration;

import com.pinwheel.anabel.external.category.Integration;
import com.pinwheel.anabel.repository.UserRepository;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = {"/application-test.properties", "/application-test-local.properties"})
@Category(Integration.class)
@WithUserDetails(value = "mike@gmail.com", userDetailsServiceBeanName = "userService")
public class ProfileEditingIntegrationTest {
    @Autowired
    private WebApplicationContext context;

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
    public void shouldUpdateGeneralInformation() throws Exception {
        final MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("firstName", "First");
        param.add("lastName", "Last");
        param.add("displayName", "Display Name");
        param.add("about", "About information");

        this.mockMvc.perform(post("/user/edit/general").params(param).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/edit"))
                .andExpect(flash().attribute("flushMessage", "Profile has been updated."))
                .andExpect(flash().attribute("flushStatus", "success"));

        var user = userRepository.findByEmail("mike@gmail.com");

        assertNotNull(user);
        assertEquals("First", user.getFirstName());
        assertEquals("Last", user.getLastName());
        assertEquals("Display Name", user.getDisplayName());
        assertEquals("About information", user.getAbout());
    }

    @Test
    @Sql(value = {"/db/fixture/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/db/fixture/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Transactional
    public void shouldRejectEditingUserIfValidationFailed() throws Exception {
        String formXPath = "//*[@id=\"app\"]//*[@action=\"/user/edit/general\"]";

        final MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("firstName", "f");
        param.add("lastName", "l");
        param.add("about", "a");

        this.mockMvc.perform(post("/user/edit/general").params(param).with(csrf()))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(xpath(formXPath + "//*[@name=\"firstName\" and contains(@class, 'is-invalid')]").exists())
                .andExpect(xpath(formXPath + "//*[@name=\"firstName\"]/..//div[contains(@class, 'invalid-feedback')]/span[1]").string(containsString("Size must be between 2 and 16.")))
                .andExpect(xpath(formXPath + "//*[@name=\"lastName\" and contains(@class, 'is-invalid')]").exists())
                .andExpect(xpath(formXPath + "//*[@name=\"lastName\"]/..//div[contains(@class, 'invalid-feedback')]/span[1]").string(containsString("Size must be between 2 and 16.")))
                .andExpect(xpath(formXPath + "//*[@name=\"about\" and contains(@class, 'is-invalid')]").exists())
                .andExpect(xpath(formXPath + "//*[@name=\"about\"]/..//div[contains(@class, 'invalid-feedback')]/span[1]").string(containsString("Size must be between 2 and 32768.")))
                .andExpect(xpath(formXPath + "//*[@name=\"displayName\" and contains(@class, 'is-invalid')]").exists())
                .andExpect(xpath(formXPath + "//*[@name=\"displayName\"]/..//div[contains(@class, 'invalid-feedback')]/span[1]").string(containsString("Must not be blank.")));
    }
}
