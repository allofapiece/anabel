package com.pinwheel.anabel.service;

import com.pinwheel.anabel.entity.SiteSetting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @version 1.0.0
 */
public interface SiteSettingService {
    Page<SiteSetting    > findAll(Pageable pageable);

    SiteSetting findByKey(String key);

    Object getValue(String key);

    Object getValue(String key, boolean useCache);

    void clearCache();
}
