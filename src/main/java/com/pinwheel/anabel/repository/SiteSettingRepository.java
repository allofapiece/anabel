package com.pinwheel.anabel.repository;

import com.pinwheel.anabel.entity.SiteSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Site Setting Repository.
 *
 * @version 1.0.0
 */

@RepositoryRestResource(collectionResourceRel = "settings", path = "settings")
public interface SiteSettingRepository extends JpaRepository<SiteSetting, Long> {
    /**
     * Finds {@link SiteSetting} entity by provided key.
     *
     * @param key target key.
     * @return found site setting entity.
     */
    SiteSetting findByKey(String key);

    /**
     * Check whether the key exists in database for provided key.
     *
     * @param key key of the setting.
     * @return whether key exists in database.
     */
    boolean existsByKey(String key);

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    <S extends SiteSetting> S save(S entity);
}
