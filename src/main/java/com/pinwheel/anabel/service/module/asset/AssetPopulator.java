package com.pinwheel.anabel.service.module.asset;

/**
 * Asset populator.
 */
public interface AssetPopulator {
    /**
     * Populates asset storage.
     *
     * @param storage storage for populating.
     * @throws Exception
     */
    void populate(AssetStorage storage) throws Exception;
}
