package com.pinwheel.anabel.integration;

import com.pinwheel.anabel.entity.dto.CaptchaResponseDto;
import com.pinwheel.anabel.external.category.Integration;
import com.pinwheel.anabel.repository.UserRepository;
import com.pinwheel.anabel.service.CaptchaService;
import org.junit.Ignore;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = {"/application-test.properties", "/application-test-local.properties"})
@Category(Integration.class)
@Ignore
public class SignUpIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private CaptchaService captchaService;

    @Test
    @Sql(value = {"/db/fixture/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/db/fixture/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void shouldSignUp() throws Exception {
        var captchaDto = new CaptchaResponseDto();
        captchaDto.setSuccess(true);
        Mockito.doReturn(captchaDto).when(captchaService).verify("captcha");

        final MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("displayName", "Andrew");
        param.add("email", "andrew@gmail.com");
        param.add("password", "Qqqq1111");
        param.add("confirmedPassword", "Qqqq1111");
        param.add("g-recaptcha-response", "captcha");

        this.mockMvc.perform(post("/signup").params(param).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"))
                .andExpect(flash().attribute("flushMessage", "Verify your email for completing registration."))
                .andExpect(flash().attribute("flushStatus", "success"));

        var user = userRepository.findByEmail("andrew@gmail.com");

        assertNotNull(user);
        assertEquals("Andrew", user.getDisplayName());
        assertEquals("andrew", user.getSlug());
    }
}
