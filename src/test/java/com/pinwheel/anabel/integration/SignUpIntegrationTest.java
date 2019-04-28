package com.pinwheel.anabel.integration;

import com.pinwheel.anabel.external.category.Integration;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = {"/application-test.properties", "/application-test-local.properties"})
@Category(Integration.class)
public class SignUpIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Sql(value = {"/db/fixture/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void shouldSignUp() throws Exception {
        this.mockMvc.perform(formLogin().user("email", "mike@gmail.com").password("Qqqq1111"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    /*@Test
    public void shouldRejectValidation() throws Exception {
        final MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("displayName", "");
        param.add("email", "");
        param.add("password", "");
        param.add("confirmedPassword", "");

        this.mockMvc.perform(post("/signup").params(param))
                .andDo(print())
                .andExpect(status().is(403))
                .andExpect(xpath("//*[@id=\"app\"]/*[contains(@action,’/signup’)]" +
                        "/*[contains(@class,’form-group’)][1]/*[contains(@class,’invalid-feedback’)]/span").exists()
                        *//*.string("size must be between 4 and 20")*//*);
    }*/
}
