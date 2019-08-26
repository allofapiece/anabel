package com.pinwheel.anabel.validation;

import com.pinwheel.anabel.entity.dto.UserChangePasswordDto;
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
public class ChangePasswordValidationTest {
    @Autowired
    private Validator validator;

    @Test
    public void shouldMatch() {
        var userChangePasswordDto = new UserChangePasswordDto();
        userChangePasswordDto.setOldPassword("Qqqq1111");
        userChangePasswordDto.setPassword("Qqqq2222");
        userChangePasswordDto.setConfirmedPassword("Qqqq2222");

        Set<ConstraintViolation<UserChangePasswordDto>> violations = validator.validate(userChangePasswordDto);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void shouldMinAndMax() {
        var userChangePasswordDto = new UserChangePasswordDto();
        userChangePasswordDto.setOldPassword("Qqqq1111");
        userChangePasswordDto.setPassword("qq");
        userChangePasswordDto.setConfirmedPassword("qq");

        Set<ConstraintViolation<UserChangePasswordDto>> violations = validator.validate(userChangePasswordDto);
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(x -> x.getMessage().equals("Password must have more characters than 4.")));

        userChangePasswordDto.setPassword("qqqqqqqqqqqqqqqqqqqqqqq");
        userChangePasswordDto.setConfirmedPassword("qqqqqqqqqqqqqqqqqqqqqqq");

        violations = validator.validate(userChangePasswordDto);
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(x -> x.getMessage().equals("Password must contain less characters than 18.")));
    }

    @Test
    public void shouldHaveOneLowerCaseCharacter() {
        var userChangePasswordDto = new UserChangePasswordDto();
        userChangePasswordDto.setOldPassword("qqqq1111");
        userChangePasswordDto.setPassword("QQQQ1111");
        userChangePasswordDto.setConfirmedPassword("QQQQ1111");

        Set<ConstraintViolation<UserChangePasswordDto>> violations = validator.validate(userChangePasswordDto);
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(x -> x.getMessage().equals("Must be one lowercase character.")));
    }
}
