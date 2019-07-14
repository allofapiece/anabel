package com.pinwheel.anabel.validation;

import com.pinwheel.anabel.entity.SiteSettingType;
import com.pinwheel.anabel.entity.Status;
import com.pinwheel.anabel.entity.dto.SiteSettingDto;
import com.pinwheel.anabel.external.category.Unit;
import com.pinwheel.anabel.repository.SiteSettingRepository;
import org.junit.experimental.categories.Category;
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
public class SiteSettingValidationTest {
    @Autowired
    private Validator validator;

    @MockBean
    private SiteSettingRepository siteSettingRepository;

    @Test
    public void shouldAllowArrayListValue() {
        Mockito.doReturn(null).when(siteSettingRepository).findByKey("newkey");

        SiteSettingDto siteSettingDto = SiteSettingDto.builder()
                .key("newkey")
                .value("new value")
                .type(SiteSettingType.ARRAY_LIST)
                .status(Status.DISABLED)
                .build();

        Set<ConstraintViolation<SiteSettingDto>> violations = validator.validate(siteSettingDto);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void shouldAllowBooleanValue() {
        Mockito.doReturn(null).when(siteSettingRepository).findByKey("newkey");

        SiteSettingDto siteSettingDto = SiteSettingDto.builder()
                .key("newkey")
                .value("true")
                .type(SiteSettingType.BOOLEAN)
                .status(Status.ACTIVE)
                .build();

        Set<ConstraintViolation<SiteSettingDto>> violations = validator.validate(siteSettingDto);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void shouldRejectIfNotTrueOfFalseForBooleanValue() {
        Mockito.doReturn(null).when(siteSettingRepository).findByKey("newkey");

        SiteSettingDto siteSettingDto = SiteSettingDto.builder()
                .key("newkey")
                .value("nottrue")
                .type(SiteSettingType.BOOLEAN)
                .status(Status.ACTIVE)
                .build();

        Set<ConstraintViolation<SiteSettingDto>> violations = validator.validate(siteSettingDto);
        assertEquals(2, violations.size());
        assertTrue(violations.stream().anyMatch(x -> x.getMessage().equals("for boolean type value can has only `true` or `false` value")));
        assertTrue(violations.stream().anyMatch(x -> x.getMessage().equals("value is not valid")));
    }

    @Test
    public void shouldRejectIfAllFieldsAreEmpty() {
        Mockito.doReturn(null).when(siteSettingRepository).findByKey("newkey");

        SiteSettingDto siteSettingDto = SiteSettingDto.builder()
                .key("")
                .value("")
                .type(null)
                .status(null)
                .build();

        Set<ConstraintViolation<SiteSettingDto>> violations = validator.validate(siteSettingDto);

        assertEquals(6, violations.size());
        assertTrue(violations.stream().anyMatch(x -> x.getMessage().equals("must not be empty") && "value".equals(x.getPropertyPath().toString())));
        assertTrue(violations.stream().anyMatch(x -> x.getMessage().equals("must not be null") && "type".equals(x.getPropertyPath().toString())));
        assertTrue(violations.stream().anyMatch(x -> x.getMessage().equals("value is not valid") && "type".equals(x.getPropertyPath().toString())));
        assertTrue(violations.stream().anyMatch(x -> x.getMessage().equals("must not be null") && "status".equals(x.getPropertyPath().toString())));
        assertTrue(violations.stream().anyMatch(x -> x.getMessage().equals("value is not valid") && "status".equals(x.getPropertyPath().toString())));
        assertTrue(violations.stream().anyMatch(x -> x.getMessage().equals("size must be between 2 and 20") && "key".equals(x.getPropertyPath().toString())));
    }

    @Test
    public void shouldRejectIfAllFieldsAreNull() {
        Mockito.doReturn(null).when(siteSettingRepository).findByKey("newkey");
        SiteSettingDto siteSettingDto = new SiteSettingDto();

        Set<ConstraintViolation<SiteSettingDto>> violations = validator.validate(siteSettingDto);
        assertEquals(6, violations.size());

        assertTrue(violations.stream().anyMatch(x -> x.getMessage().equals("must not be empty") && "value".equals(x.getPropertyPath().toString())));
        assertTrue(violations.stream().anyMatch(x -> x.getMessage().equals("must not be null") && "status".equals(x.getPropertyPath().toString())));
        assertTrue(violations.stream().anyMatch(x -> x.getMessage().equals("value is not valid") && "status".equals(x.getPropertyPath().toString())));
        assertTrue(violations.stream().anyMatch(x -> x.getMessage().equals("must not be null") && "type".equals(x.getPropertyPath().toString())));
        assertTrue(violations.stream().anyMatch(x -> x.getMessage().equals("value is not valid") && "type".equals(x.getPropertyPath().toString())));
        assertTrue(violations.stream().anyMatch(x -> x.getMessage().equals("must not be null") && "key".equals(x.getPropertyPath().toString())));
    }

    @Test
    public void shouldRejectIfKeyAlreadyExists() {
        Mockito.doReturn(true).when(siteSettingRepository).existsByKey("existentKey");
        SiteSettingDto siteSettingDto = SiteSettingDto.builder()
                .key("existentKey")
                .value("true")
                .type(SiteSettingType.BOOLEAN)
                .status(Status.ACTIVE)
                .build();

        Set<ConstraintViolation<SiteSettingDto>> violations = validator.validate(siteSettingDto);

        assertEquals(2, violations.size());
        assertTrue(violations.stream().anyMatch(x -> x.getMessage().equals("value is not valid") && x.getPropertyPath().toString().isEmpty()));
        assertTrue(violations.stream().anyMatch(x -> x.getMessage().equals("this key already exists") && "key".equals(x.getPropertyPath().toString())));
    }
}
