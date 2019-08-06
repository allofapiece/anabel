package com.pinwheel.anabel.service.module.asset;

import com.pinwheel.anabel.service.module.asset.entity.Asset;

import java.util.List;
import java.util.Map;

/**
 * Storage for asset entities. Contains methods for managing of assets.
 *
 * @version 1.0.0
 */
public interface AssetStorage {
    /**
     * Adds new asset.
     *
     * @param asset asset which will be added to storage.
     */
    void add(Asset asset);

    /**
     * Adds new asset related with specific template name.
     *
     * @param asset    asset which will be added to storage.
     * @param template related template.
     */
    void add(Asset asset, String template);

    /**
     * Removes asset from storage.
     *
     * @param asset asset to remove.
     * @return whether asset has been removed.
     */
    boolean remove(Asset asset);

    /**
     * Returns list of grouped assets.
     *
     * @param template template name to retrieve related assets.
     * @param key      key for grouping.
     * @return grouped assets.
     */
    List<Asset> getGrouped(String template, Object key);

    /**
     * Returns map of not grouped assets related with passed template name.
     *
     * @param template template name to retrieve related assets.
     * @return not grouped assets.
     */
    Map<String, List<Asset>> getAllByTemplate(String template);

    /**
     * Returns map of not grouped default assets.
     *
     * @return not grouped default assets.
     */
    Map<String, List<Asset>> getDefault();
}
