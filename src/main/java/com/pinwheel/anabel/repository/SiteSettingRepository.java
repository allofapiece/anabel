package com.pinwheel.anabel.repository;

import com.pinwheel.anabel.entity.SiteSetting;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Site Setting Repository.
 *
 * @version 1.0.0
 */
public interface SiteSettingRepository extends JpaRepository<SiteSetting, Long> {
    /**
     * Finds {@link SiteSetting} entity by provided email.
     *
     * @param key target key.
     * @return found site setting entity.
     */
    SiteSetting findByKey(String key);
}
