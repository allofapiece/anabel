package com.pinwheel.anabel.service;

import com.pinwheel.anabel.entity.Section;

import java.util.List;

/**
 * @version 1.0.0
 */
public interface SectionService {
    boolean add(Section section);

    /**
     * Returns all sections.
     *
     * @return list of sections.
     */
    List<Section> findAll();

    /**
     * Returns all sections.
     *
     * @return list of sections.
     */
    List<Section> findAll(boolean useCache);

    boolean clearCache(boolean populateAfterClear);

    boolean clearCache();

    boolean delete(Section section);

    long count();
}
