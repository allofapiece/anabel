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
     * Finds {@link SiteSetting} entity by provided key.
     *
     * @param key target key.
     * @return found site setting entity.
     */
    SiteSetting findByKey(String key);

    /**
     * Check whether key exists in database for provided key.
     *
     * @param key key of the setting.
     * @return whether key exists in database.
     */
    boolean existsByKey(String key);
}
