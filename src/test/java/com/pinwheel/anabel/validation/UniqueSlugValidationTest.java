package com.pinwheel.anabel.validation;

import com.pinwheel.anabel.entity.User;
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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(locations = {
        "/application-test.properties",
        "/application-test-local.properties"
})
@Category(Unit.class)
public class UniqueSlugValidationTest {
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
    public void shouldRejectIfAlreadyExistsWithoutId() {
        Mockito.doReturn(true).when(userRepository).existsBySlug(eq("newslug"));

        var user = new User();
        user.setSlug("newslug");

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.stream().anyMatch(x -> x.getMessageTemplate().contains("slug.error.taken")));
    }

    @Test
    public void shouldRejectIfAlreadyExists() {
        Mockito.doReturn(true).when(userRepository).existsBySlugAndIdNot("newslug", 10L);

        var user = new User();
        user.setId(10L);
        user.setSlug("newslug");

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.stream().anyMatch(x -> x.getMessageTemplate().contains("slug.error.taken")));
    }

    @Test
    public void shouldRejectIfPredefinedName() {
        Mockito.doReturn(false).when(userRepository).existsBySlugAndIdNot(any(String.class), any(Long.class));

        var user = new User();
        user.setId(10L);
        user.setSlug("admin");

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.stream().anyMatch(x -> x.getMessageTemplate().contains("slug.error.taken")));
    }

    @Test
    public void shouldAccept() {
        Mockito.doReturn(false).when(userRepository).existsBySlugAndIdNot(any(String.class), any(Long.class));

        var user = new User();
        user.setId(10L);
        user.setSlug("user");

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.stream().anyMatch(x -> x.getMessageTemplate().contains("slug.error.taken")));
    }
}
