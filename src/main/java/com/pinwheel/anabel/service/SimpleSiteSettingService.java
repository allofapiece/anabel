package com.pinwheel.anabel.service;

import com.pinwheel.anabel.entity.SiteSetting;
import com.pinwheel.anabel.entity.SiteSettingType;
import com.pinwheel.anabel.repository.SiteSettingRepository;
import com.pinwheel.anabel.service.converter.setting.ArrayListSettingConverter;
import com.pinwheel.anabel.service.converter.setting.BooleanSettingConverter;
import com.pinwheel.anabel.service.converter.setting.SettingConverter;
import com.pinwheel.anabel.service.converter.setting.StringSettingConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;


/**
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class SimpleSiteSettingService implements SiteSettingService {
    private final SiteSettingRepository siteSettingRepository;

    private Map<String, Object> cache = new HashMap<>();

    private Map<SiteSettingType, SettingConverter> typeMap = new HashMap<>();

    @PostConstruct
    public void init() {
        typeMap.put(SiteSettingType.BOOLEAN, new BooleanSettingConverter());
        typeMap.put(SiteSettingType.ARRAY_LIST, new ArrayListSettingConverter());
        typeMap.put(SiteSettingType.STRING, new StringSettingConverter());
    }

    @Override
    public Page<SiteSetting> findAll(Pageable pageable) {
        return siteSettingRepository.findAll(pageable);
    }

    @Override
    public SiteSetting findByKey(String key) {
        return siteSettingRepository.findByKey(key);
    }

    @Override
    public Object getValue(String key) {
        return getValue(key, true);
    }

    @Override
    public Object getValue(String key, boolean useCache) {
        if (useCache && cache.containsKey(key)) {
            return cache.get(key);
        }

        SiteSetting siteSetting = this.findByKey(key);

        if (siteSetting == null || !typeMap.containsKey(siteSetting.getType())) {
            return null;
        }

        SettingConverter converter = typeMap.get(siteSetting.getType());

        if (converter == null) {
            return null;
        }

        Object value = converter.convertToJava(siteSetting.getValue());

        cache.put(key, value);

        return value;
    }

    @Override
    public void clearCache() {
        cache.clear();
    }
}
