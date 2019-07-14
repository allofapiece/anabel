package com.pinwheel.anabel.integration;

import com.pinwheel.anabel.entity.SiteSetting;
import com.pinwheel.anabel.external.category.Integration;
import com.pinwheel.anabel.service.SiteSettingService;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = {"/application-test.properties", "/application-test-local.properties"})
@Category(Integration.class)
@WithMockUser(password = "Qqqq1111", username = "mike@gmail.com", authorities = {"USER", "ADMIN"})
public class SiteSettingIntegrationTest {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private SiteSettingService siteSettingService;

    HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
    }

    @Test
    public void shouldRejectValidationIfEmptyField() throws Exception {
        String fieldXpath = "//*[@id=\"app\"]//*[@action=\"/admin/settings/form\"]//*[@name=\"%s\"]/..//div[contains(@class, 'invalid-feedback')]/span[%s]";

        final MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("key", "");
        param.add("value", "");
        param.add("type", "");
        param.add("status", "");

        CsrfToken csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());

        this.mockMvc.perform(post("/admin/settings/form").sessionAttr("_csrf", csrfToken).params(param))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(xpath(String.format(fieldXpath, "key", "1")).string(containsString("size must be")))
                .andExpect(xpath(String.format(fieldXpath, "value", "1")).string(is("must not be empty")))
                .andExpect(xpath(String.format(fieldXpath, "type", ".=\"must not be null\"")).exists())
                .andExpect(xpath(String.format(fieldXpath, "type", ".=\"value is not valid\"")).exists())
                .andExpect(xpath(String.format(fieldXpath, "status", ".=\"must not be null\"")).exists())
                .andExpect(xpath(String.format(fieldXpath, "status", ".=\"value is not valid\"")).exists());
    }

    @Test
    @Sql(value = {"/db/fixture/create-site-settings-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/db/fixture/delete-site-setting.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldRejectValidationIfKeyAlreadyExistsAndValueNotTrueOrFalse() throws Exception {
        String fieldXpath = "//*[@id=\"app\"]//*[@action=\"/admin/settings/form\"]//*[@name=\"%s\"]/..//div[contains(@class, 'invalid-feedback')]/span[%d]";

        final MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("key", "key1");
        param.add("value", "not true");
        param.add("type", "BOOLEAN");
        param.add("status", "ACTIVE");

        CsrfToken csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());

        this.mockMvc.perform(post("/admin/settings/form").sessionAttr("_csrf", csrfToken).params(param))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(xpath(String.format(fieldXpath, "key", 1)).string(containsString("this key already exists")))
                .andExpect(xpath(String.format(fieldXpath, "value", 1)).string(is("for boolean type value can has only `true` or `false` value")))
                .andExpect(xpath(String.format(fieldXpath, "type", 1)).doesNotExist())
                .andExpect(xpath(String.format(fieldXpath, "status", 1)).doesNotExist());
    }

    @Test
    @Sql(value = {"/db/fixture/delete-site-setting.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void shouldAddSettingSuccessfully() throws Exception {
        final MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("key", "newkey");
        param.add("value", "new value");
        param.add("type", "STRING");
        param.add("status", "ACTIVE");

        CsrfToken csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());

        this.mockMvc.perform(post("/admin/settings/form").sessionAttr("_csrf", csrfToken).params(param))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/settings"))
                .andExpect(flash().attribute("flushMessage", "Setting has been saved successfully."))
                .andExpect(flash().attribute("flushStatus", "success"));

        SiteSetting setting = siteSettingService.findByKey("newkey");

        assertNotNull(setting);
        assertEquals("new value", setting.getValue());
    }

    @Test
    @Sql(value = {"/db/fixture/create-site-settings-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/db/fixture/delete-site-setting.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldDisplaySettingsByPagesAndDescOrder() throws Exception {
        String paginationXpath = "//*[@id=\"app\"]//*[contains(@class, 'pagination')]";

        this.mockMvc.perform(get("/admin/settings").param("size", "2"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(xpath(paginationXpath + "/li").nodeCount(3))
                .andExpect(xpath(paginationXpath + "/li[1 and contains(@class, 'active')]").exists())
                .andExpect(xpath("//*[@id=\"app\"]//*[contains(@class, 'table')]//tr[2]/td[1]").string(is("6")));
    }

    @Test
    @Sql(value = {"/db/fixture/delete-site-setting.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void shouldDisplayNoSettingsMessageAndNoPagination() throws Exception {
        final String paginationXpath = "//*[@id=\"app\"]//*[contains(@class, 'pagination')]";

        this.mockMvc.perform(get("/admin/settings"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(xpath(paginationXpath).doesNotExist())
                .andExpect(xpath("//*[@id=\"app\"]/main/div/div/div[3]/p").string(is("No settings yet.")));
    }

    @Test
    @Sql(value = {"/db/fixture/create-site-settings-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/db/fixture/delete-site-setting.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldUpdateExistentSetting() throws Exception {
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id", "1");
        params.add("key", "updatedkey");
        params.add("value", "updated value");
        params.add("type", "ARRAY_LIST");
        params.add("status", "ACTIVE");

        CsrfToken csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());

        this.mockMvc.perform(post("/admin/settings/form").sessionAttr("_csrf", csrfToken).params(params))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/settings"))
                .andExpect(flash().attribute("flushMessage", "Setting has been saved successfully."))
                .andExpect(flash().attribute("flushStatus", "success"));

        assertNull(siteSettingService.findByKey("key1"));

        SiteSetting siteSetting = siteSettingService.findByKey("updatedkey");

        assertNotNull(siteSetting);

        assertEquals("updated value", siteSetting.getValue());
        assertEquals(Long.valueOf(1), siteSetting.getId());
    }

    @Test
    @Sql(value = {"/db/fixture/create-site-settings-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/db/fixture/delete-site-setting.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldDeleteSiteSetting() throws Exception {
        long initialCount = siteSettingService.count();

        this.mockMvc.perform(get("/admin/settings/delete/" + 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/settings"))
                .andExpect(flash().attribute("flushMessage", "Setting has been removed successfully."))
                .andExpect(flash().attribute("flushStatus", "success"));

        assertNull(siteSettingService.findByKey("key1"));
        assertEquals(initialCount - 1, siteSettingService.count());
    }
}
