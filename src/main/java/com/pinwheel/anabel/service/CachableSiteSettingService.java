package com.pinwheel.anabel.service;

import com.pinwheel.anabel.entity.SiteSetting;
import com.pinwheel.anabel.entity.SiteSettingType;
import com.pinwheel.anabel.repository.SiteSettingRepository;
import com.pinwheel.anabel.service.converter.ConverterNotFoundException;
import com.pinwheel.anabel.service.converter.setting.ArrayListSettingConverter;
import com.pinwheel.anabel.service.converter.setting.BooleanSettingConverter;
import com.pinwheel.anabel.service.converter.setting.SettingConverter;
import com.pinwheel.anabel.service.converter.setting.StringSettingConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


/**
 * Implementation of {@link SiteSettingService} interface.
 *
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class CachableSiteSettingService implements SiteSettingService {
    /**
     * Inject of {@link SiteSettingRepository} bean.
     */
    private final SiteSettingRepository siteSettingRepository;

    /**
     * Cache of site settings where key is key of site setting and value is site setting instance.
     */
    private Map<String, Object> cache = new HashMap<>();

    /**
     * Types map. Mapping for site setting type to converter that can convert specific type of site setting.
     */
    private Map<SiteSettingType, SettingConverter> typeMap = Map.of(
            SiteSettingType.BOOLEAN, new BooleanSettingConverter(),
            SiteSettingType.ARRAY_LIST, new ArrayListSettingConverter(),
            SiteSettingType.STRING, new StringSettingConverter()
    );

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<SiteSetting> findAll(Pageable pageable) {
        return siteSettingRepository.findAll(pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SiteSetting findByKey(String key) {
        return siteSettingRepository.findByKey(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getValue(String key) {
        return getValue(key, true);
    }

    /**
     * {@inheritDoc}
     */
    public Object getValue(String key, boolean useCache) {
        if (useCache && cache.containsKey(key)) {
            return cache.get(key);
        }

        SiteSetting siteSetting = this.findByKey(key);

        if (siteSetting == null) {
            throw new RuntimeException("Setting cannot be found by passed key.");
        }

        SettingConverter converter = typeMap.get(siteSetting.getType());

        if (converter == null) {
            throw new ConverterNotFoundException();
        }

        Object value = converter.convertToJava(siteSetting.getValue());

        cache.put(key, value);

        return value;
    }

    /**
     * {@inheritDoc}
     */
    public void clearCache() {
        cache.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SiteSetting save(SiteSetting siteSetting) {
        clearCache();

        return siteSettingRepository.save(siteSetting);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(SiteSetting siteSetting) {
        this.delete(siteSetting.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) {
        clearCache();
        siteSettingRepository.deleteById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long count() {
        return siteSettingRepository.count();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean exists(String key) {
        return siteSettingRepository.existsByKey(key);
    }
}
