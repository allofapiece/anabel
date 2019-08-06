package com.pinwheel.anabel.service.module.asset;

import com.pinwheel.anabel.service.module.asset.entity.Asset;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Map Asset Storage. Implementation of {@link AssetStorage} that uses {@link Map} as storage of assets.
 *
 * @version 1.0.0
 */
@Service
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MapAssetStorage implements AssetStorage {
    /**
     * Storage of assets.
     */
    private Map<String, Map<String, List<Asset>>> map = new HashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(Asset asset) {
        add(asset, asset.getTemplate());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(Asset asset, String template) {
        if (!map.containsKey(asset.getTemplate())) {
            map.put(asset.getTemplate(), new HashMap<>());
        }

        Map<String, List<Asset>> assets = map.get(asset.getTemplate());

        if (!assets.containsKey(asset.getName())) {
            assets.put(asset.getName(), new ArrayList<>());
        }

        List<Asset> assetsList = assets.get(asset.getName());
        asset.setTemplate(template);
        assetsList.add(asset);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(Asset asset) {
        List<Asset> assetsList = map.get(asset.getTemplate()).get(asset.getName());
        return assetsList != null && assetsList.remove(asset);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Asset> getGrouped(String template, Object key) {
        return getGrouped(template, key, true);
    }

    public List<Asset> getGrouped(String template, Object key, boolean withDefault) {
        return getAllByTemplate(template, withDefault).getOrDefault(key, Collections.emptyList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, List<Asset>> getDefault() {
        return map.get("default");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, List<Asset>> getAllByTemplate(String template) {
        return getAllByTemplate(template, true);
    }

    /**
     * Variant of {@link MapAssetStorage#getAllByTemplate(String)} which also set up default assets.
     *
     * @param template    template name to retrieve related assets.
     * @param withDefault whether need default assets.
     * @return not grouped assets.
     */
    public Map<String, List<Asset>> getAllByTemplate(String template, boolean withDefault) {
        var assets = new HashMap<>(map.getOrDefault(template, Collections.emptyMap()));

        if (!withDefault || "default".equals(template)) {
            return assets;
        }

        var defaultAssets = getDefault();

        if (assets.isEmpty()) {
            return defaultAssets;
        }

        for (Map.Entry<String, List<Asset>> entry : defaultAssets.entrySet()) {
            String name = entry.getKey();

            if (!assets.containsKey(name)) {
                assets.put(name, new ArrayList<>());
            } else {
                assets.put(name, new ArrayList<>(assets.get(name)));
            }

            List<Asset> assetList = assets.get(name);

            assetList.addAll(0, entry.getValue());
            assets.put(name, assetList);
        }

        return assets;
    }
}
