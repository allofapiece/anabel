package com.pinwheel.anabel.service.module.asset;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Simple asset manager. Contains logic for asset storage manipulating.
 */
@Service
@RequiredArgsConstructor
public class SimpleAssetManager implements AssetManager {
    /**
     * Injection of {@link AssetStorage} bean.
     */
    private final AssetStorage storage;

    /**
     * Injection of {@link AssetPopulator} bean.
     */
    private final AssetPopulator populator;

    /**
     * Populates the assets storage.
     *
     * @throws Exception
     */
    @PostConstruct
    public void init() throws Exception {
        populator.populate(storage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AssetStorage getStorage() {
        return this.storage;
    }
}
