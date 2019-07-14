package com.pinwheel.anabel.service;

import com.pinwheel.anabel.entity.SiteSetting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Site setting service. Contains logic for {@link SiteSetting} entity.
 *
 * @version 1.0.0
 */
public interface SiteSettingService {
    /**
     * Returns site setting by pages.
     *
     * @param pageable paginator.
     * @return page of site settings.
     */
    Page<SiteSetting> findAll(Pageable pageable);

    /**
     * Returns site setting by passed key.
     *
     * @param key key of the site setting.
     * @return site setting instance.
     */
    SiteSetting findByKey(String key);

    /**
     * Returns value by passed key. {@code useCache} parameter defines whether setting value can be retrieved from
     * cache.
     *
     * @param key key of the setting.
     * @return setting instance.
     */
    Object getValue(String key);

    /**
     * Returns value by passed key. {@code useCache} parameter defines whether setting value can be retrieved from
     * cache.
     */
    Object getValue(String key, boolean useCache);

    /**
     * Clears cache of settings.
     */
    void clearCache();

    /**
     * Saves setting instance.
     *
     * @param siteSetting setting instance.
     * @return saved {@link SiteSetting} instance.
     */
    SiteSetting save(SiteSetting siteSetting);

    /**
     * Deletes setting.
     *
     * @param siteSetting setting for deleting.
     */
    void delete(SiteSetting siteSetting);

    /**
     * Deletes setting by passed id.
     *
     * @param id id of setting.
     */
    void delete(Long id);

    /**
     * Returns count of settings.
     *
     * @return count of settings.
     */
    long count();

    /**
     * Check whether key exists in database for provided key.
     *
     * @param key key of the setting.
     * @return whether key exists in database.
     */
    boolean exists(String key);
}
