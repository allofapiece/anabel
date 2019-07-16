package com.pinwheel.anabel.service;

import com.pinwheel.anabel.external.category.Unit;
import com.pinwheel.anabel.repository.UserRepository;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(locations = {
        "/application-test.properties",
        "/application-test-local.properties"
})
@Category(Unit.class)
public class SimpleSlugGeneratorUnitTest {

    @Autowired
    private SlugGenerator slugGenerator;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void shouldIterationOf() {
        assertEquals(0, slugGenerator.iterationOf("long-slug"));
        assertEquals(0, slugGenerator.iterationOf("long-slug-0"));
        assertEquals(32, slugGenerator.iterationOf("long-slug-32"));
        assertEquals(1, slugGenerator.iterationOf("slug-1"));
        assertEquals(1, slugGenerator.iterationOf("long-slug-1"));
        assertEquals(2, slugGenerator.iterationOf("long-slug-13-2"));
    }

    @Test
    public void shouldCreateNew() {
        assertEquals("slug", slugGenerator.createNew("slug", 0));
        assertEquals("slug-2", slugGenerator.createNew("slug", 2));
    }

    @Test
    public void shouldPredicate() {
        assertFalse(slugGenerator.predicate(1, List.of("slug", "slug-1")));
        assertFalse(slugGenerator.predicate(1, List.of("slug", "slug-1", "slug-2")));
        assertFalse(slugGenerator.predicate(0, List.of("slug")));
        assertTrue(slugGenerator.predicate(1, List.of("slug", "slug-2")));
        assertTrue(slugGenerator.predicate(0, List.of("slug-1", "slug-2")));
        assertTrue(slugGenerator.predicate(2, List.of("slug-0", "slug-1", "slug-3")));
    }

    @Test
    public void shouldFilter() {
        assertEquals("long-slug", slugGenerator.filterString("Long Slug"));
        assertEquals("long-slug", slugGenerator.filterString("long slug"));
        assertEquals("long-slug", slugGenerator.filterString("LONG SLUG"));
        assertEquals("long-slug", slugGenerator.filterString("LONG-SLUG"));
        assertEquals("long-slug", slugGenerator.filterString("LONG         SLUG"));
        assertEquals("long-slug", slugGenerator.filterString("    LONG         SLUG   "));
        assertEquals("long-slug-2", slugGenerator.filterString("Long slug 2"));
        assertEquals("long-3-slug", slugGenerator.filterString("ываlong ыуаыуа3 slug"));
        assertEquals("long3slug", slugGenerator.filterString("ываlongыуаыуа3slug"));
    }

    @Test
    public void shouldCreateSlugBetween() {
        Mockito.doReturn(List.of("slug", "slug-1", "slug-3")).when(userRepository).findSlugsBySlugRegexp("slug");
        String slug = slugGenerator.slug("slug", (x) -> userRepository.findSlugsBySlugRegexp(x));
        assertEquals("slug-2", slug);
    }

    @Test
    public void shouldCreateSlugFirst() {
        Mockito.doReturn(List.of("slug-1", "slug-2", "slug-3")).when(userRepository).findSlugsBySlugRegexp("slug");
        String slug = slugGenerator.slug("slug", (x) -> userRepository.findSlugsBySlugRegexp(x));
        assertEquals("slug", slug);
    }

    @Test
    public void shouldCreateSlugLast() {
        Mockito.doReturn(List.of("slug", "slug-1", "slug-2")).when(userRepository).findSlugsBySlugRegexp("slug");
        String slug = slugGenerator.slug("slug", (x) -> userRepository.findSlugsBySlugRegexp(x));
        assertEquals("slug-3", slug);
    }

    @Test
    public void shouldCreateSlugOne() {
        Mockito.doReturn(List.of()).when(userRepository).findSlugsBySlugRegexp("slug");
        String slug = slugGenerator.slug("slug", (x) -> userRepository.findSlugsBySlugRegexp(x));
        assertEquals("slug", slug);
    }

    @Test
    public void shouldCreateSlugWithZeroInTheList() {
        Mockito.doReturn(List.of("slug-0", "slug-2")).when(userRepository).findSlugsBySlugRegexp("slug");
        String slug = slugGenerator.slug("slug", (x) -> userRepository.findSlugsBySlugRegexp(x));
        assertEquals("slug-1", slug);

        Mockito.doReturn(List.of("slug-0")).when(userRepository).findSlugsBySlugRegexp("slug");
        slug = slugGenerator.slug("slug", (x) -> userRepository.findSlugsBySlugRegexp(x));
        assertEquals("slug-1", slug);

        Mockito.doReturn(List.of("slug-0", "slug-1")).when(userRepository).findSlugsBySlugRegexp("slug");
        slug = slugGenerator.slug("slug", (x) -> userRepository.findSlugsBySlugRegexp(x));
        assertEquals("slug-2", slug);
    }

    @Test
    public void shouldCreateSlugWithLongSpaces() {
        Mockito.doReturn(List.of("slug", "slug-1", "slug-5", "slug-6", "slug-20")).when(userRepository).findSlugsBySlugRegexp("slug");
        String slug = slugGenerator.slug("slug", (x) -> userRepository.findSlugsBySlugRegexp(x));
        assertEquals("slug-2", slug);
    }
}
