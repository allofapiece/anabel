package com.pinwheel.anabel.service;

import com.pinwheel.anabel.entity.SiteSetting;
import com.pinwheel.anabel.entity.SiteSettingType;
import com.pinwheel.anabel.external.category.Unit;
import com.pinwheel.anabel.repository.SiteSettingRepository;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Category(Unit.class)
public class CachableSiteSettingServiceUnitTest {

    @InjectMocks
    private CachableSiteSettingService siteSettingService;

    @Mock
    private SiteSettingRepository siteSettingRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        siteSettingService.clearCache();
    }

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
    }

    @Test
    public void shouldClearCacheAfterSaving() {
        String booleanKey = "booleanKey";

        SiteSetting stringSetting = SiteSetting.builder()
                .key("stringKey")
                .value("stringValue")
                .type(SiteSettingType.STRING)
                .build();

        SiteSetting booleanSetting = SiteSetting.builder()
                .key(booleanKey)
                .value("true")
                .type(SiteSettingType.BOOLEAN)
                .build();

        Mockito.doReturn(stringSetting).when(siteSettingRepository).save(stringSetting);
        Mockito.doReturn(booleanSetting).when(siteSettingRepository).findByKey(booleanKey);

        siteSettingService.getValue(booleanKey);

        siteSettingService.save(stringSetting);

        siteSettingService.getValue(booleanKey);

        Mockito.verify(siteSettingRepository, Mockito.times(2)).findByKey(booleanKey);
    }

    @Test
    public void shouldThrowExceptionForNonExistentKey() {
        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> siteSettingService.getValue("nonexistent")
        );
        assertEquals("Setting cannot be found by passed key.", ex.getMessage());
    }
}
