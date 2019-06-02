package com.pinwheel.anabel.service;

import com.pinwheel.anabel.entity.Section;
import com.pinwheel.anabel.repository.SectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

/**
 * Section service. Provides logic for site sections.
 *
 * @version 1.0.0
 */
@RequiredArgsConstructor
@Service
@Transactional
public class SimpleSectionService implements SectionService {
    /**
     * Injection of section repository.
     */
    private final SectionRepository sectionRepository;

    private List<Section> sectionsCache = new LinkedList<>();

    @Override
    public boolean add(Section section) {
        section = sectionRepository.save(section);

        clearCache();

        return section.getId() != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Section> findAll() {
        return this.findAll(true);
    }

    @Override
    public List<Section> findAll(boolean useCache) {
        if (useCache) {
            if (sectionsCache.size() == 0) {
                sectionsCache = sectionRepository.findAll();
            }

            return sectionsCache;
        }

        return sectionRepository.findAll();
    }

    @Override
    public boolean clearCache() {
        return clearCache(true);
    }

    @Override
    public boolean clearCache(boolean populateAfterClear) {
        sectionsCache.clear();

        if (populateAfterClear) {
            sectionsCache = sectionRepository.findAll();
        }

        return true;
    }

    @Override
    public boolean delete(Section section) {
        sectionRepository.delete(section);

        clearCache();

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long count() {
        return sectionRepository.count();
    }
}
