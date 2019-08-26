package com.pinwheel.anabel.validation;

import com.pinwheel.anabel.entity.dto.UserEditGeneralDto;
import com.pinwheel.anabel.external.category.Unit;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(locations = {
        "/application-test.properties",
        "/application-test-local.properties"
})
@Category(Unit.class)
public class EditGeneralValidationTest {
    @Autowired
    private Validator validator;

    @Test
    public void shouldMinAndMax() {
        var dto = UserEditGeneralDto.builder()
                .firstName("d")
                .lastName("d")
                .about("d")
                .displayName("d")
                .build();

        Set<ConstraintViolation<UserEditGeneralDto>> violations = validator.validate(dto);
        assertEquals(4, violations.size());
        assertTrue(violations.stream().anyMatch(x -> x.getMessage().equals("Size must be between 2 and 16.") && x.getPropertyPath().toString().equals("firstName")));
        assertTrue(violations.stream().anyMatch(x -> x.getMessage().equals("Size must be between 2 and 16.") && x.getPropertyPath().toString().equals("lastName")));
        assertTrue(violations.stream().anyMatch(x -> x.getMessage().equals("Size must be between 2 and 32.") && x.getPropertyPath().toString().equals("displayName")));
        assertTrue(violations.stream().anyMatch(x -> x.getMessage().equals("Size must be between 2 and 32768.") && x.getPropertyPath().toString().equals("about")));
    }

    @Test
    public void shouldNotBeEmpty() {
        var dto = new UserEditGeneralDto();

        Set<ConstraintViolation<UserEditGeneralDto>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(x -> x.getMessage().equals("Must not be blank.") && x.getPropertyPath().toString().equals("displayName")));
    }
}
