package com.pinwheel.anabel.validation;

import com.pinwheel.anabel.entity.dto.UserSlugDto;
import com.pinwheel.anabel.external.category.Unit;
import com.pinwheel.anabel.repository.UserRepository;
import com.pinwheel.anabel.service.SiteSettingService;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(locations = {
        "/application-test.properties",
        "/application-test-local.properties"
})
@Category(Unit.class)
public class SlugValidationTest {
    @Autowired
    private Validator validator;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private SiteSettingService siteSettingService;

    @BeforeEach
    public void beforeEach() {
        Mockito.doReturn(List.of("admin")).when(siteSettingService).getValue("slugTakenKeywords");
    }

    @Test
    public void shouldBeNotNull() {
        var slugDto = new UserSlugDto();

        Set<ConstraintViolation<UserSlugDto>> violations = validator.validate(slugDto);
        assertEquals(1, violations.size());
        assertEquals("Must not be null.", violations.stream().findFirst().get().getMessage());
    }

    @Test
    public void shouldMin4Max32() {
        var slugDto = new UserSlugDto("sl");

        Set<ConstraintViolation<UserSlugDto>> violations = validator.validate(slugDto);
        assertEquals(1, violations.size());
        assertEquals("Size must be between 4 and 32.", violations.stream().findFirst().get().getMessage());

        slugDto = new UserSlugDto("slugslugslugslugslugslugslugslugslugslugslugslugslugslugslugslugslug");

        violations = validator.validate(slugDto);
        assertEquals(1, violations.size());
        assertEquals("Size must be between 4 and 32.", violations.stream().findFirst().get().getMessage());
    }

    @Test
    public void shouldRejectExistent() {
        Mockito.doReturn(true).when(userRepository).existsBySlug("newslug");

        var slugDto = new UserSlugDto("newslug");

        Set<ConstraintViolation<UserSlugDto>> violations = validator.validate(slugDto);
        assertEquals(1, violations.size());
        assertEquals("This address is already taken.", violations.stream().findFirst().get().getMessage());
    }

    @Test
    public void shouldRejectIfNotMatchPattern() {
        var slugDto = new UserSlugDto("new_slug");

        Set<ConstraintViolation<UserSlugDto>> violations = validator.validate(slugDto);
        assertEquals(1, violations.size());
        assertEquals("You can use a-z, 0-9 and '-' for link.", violations.stream().findFirst().get().getMessage());
    }

    @Test
    public void shouldRejectIfSlugIsTakenKeyword() {
        var slugDto = new UserSlugDto("admin");

        Set<ConstraintViolation<UserSlugDto>> violations = validator.validate(slugDto);
        assertEquals(1, violations.size());
        assertEquals("This address is already taken.", violations.stream().findFirst().get().getMessage());
    }
}
