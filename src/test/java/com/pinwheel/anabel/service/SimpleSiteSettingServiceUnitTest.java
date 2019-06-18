package com.pinwheel.anabel.service;

import com.pinwheel.anabel.entity.SiteSetting;
import com.pinwheel.anabel.entity.SiteSettingType;
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

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(locations = {
        "/application-test.properties",
        "/application-test-local.properties"
})
@Category(Unit.class)
public class SimpleSiteSettingServiceUnitTest {

    @Autowired
    private SiteSettingService siteSettingService;

    @MockBean
    private SiteSettingRepository siteSettingRepository;

    @Test
    public void shouldGetValidValueByKeyAndShouldSaveValueInCache() {
        String key = "testKey";

        Mockito.doReturn(SiteSetting.builder()
                .key(key)
                .value("testValue")
                .type(SiteSettingType.STRING)
                .build()).when(siteSettingRepository).findByKey(key);

        assertEquals(siteSettingService.findByKey(key).getValue(), "testValue");
        assertEquals(siteSettingService.getValue(key, false), "testValue");
        assertEquals(siteSettingService.getValue(key, true), "testValue");

        Mockito.verify(siteSettingRepository, Mockito.times(2)).findByKey(key);
    }

    @Test
    public void shouldResolveConverterAndConvert() {
        SiteSetting stringSetting = SiteSetting.builder()
                .key("stringKey")
                .value("stringValue")
                .type(SiteSettingType.STRING)
                .build();

        SiteSetting booleanSetting = SiteSetting.builder()
                .key("booleanKey")
                .value("true")
                .type(SiteSettingType.BOOLEAN)
                .build();

        SiteSetting arrayListSetting = SiteSetting.builder()
                .key("arrayListKey")
                .value("array list value")
                .type(SiteSettingType.ARRAY_LIST)
                .build();

        Mockito.doReturn(stringSetting).when(siteSettingRepository).findByKey("stringKey");
        Mockito.doReturn(booleanSetting).when(siteSettingRepository).findByKey("booleanKey");
        Mockito.doReturn(arrayListSetting).when(siteSettingRepository).findByKey("arrayListKey");

        assertEquals(siteSettingService.getValue("stringKey"), "stringValue");
        assertEquals(siteSettingService.getValue("booleanKey"), true);
        assertEquals(siteSettingService.getValue("arrayListKey"), new ArrayList<>(Arrays.asList("array", "list", "value")));
        assertNull(siteSettingService.getValue("nonexistent"));
    }
}
