package com.pinwheel.anabel.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.util.List;
import java.util.Locale;
import java.util.OptionalInt;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

/**
 * Simple slug generator. Implementation of {@link SlugGenerator} interface.
 *
 * @version 1.0.0
 */
@Service
public class SimpleSlugGenerator implements SlugGenerator {
    /**
     * Pattern for filtering all characters instead of latin letters, numeric and `-` characters.
     */
    private static final Pattern LATIN = Pattern.compile("[^\\w-]");

    /**
     * Pattern for filtering whitespaces.
     */
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public String slug(String target, Function<String, List<String>> existentSlugsCallback) {
        String filtered = filterString(target);
        List<String> slugs = existentSlugsCallback.apply(filtered);

        if (slugs.isEmpty()) {
            return filtered;
        }

        OptionalInt firstIndex = IntStream.range(0, slugs.size()).filter(i -> predicate(i, slugs)).findFirst();

        if (firstIndex.isEmpty()) {
            return createNew(filtered, slugs.size());
        }

        if (firstIndex.getAsInt() == 0) {
            return createNew(filtered, 0);
        }

        return createNew(filtered, iterationOf(slugs.get(firstIndex.getAsInt() - 1)) + 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String filterString(String target) {
        target = target.trim().replaceAll(" +", " ");
        String noWhitespace = WHITESPACE.matcher(target.trim()).replaceAll("-");
        String normalized = Normalizer.normalize(noWhitespace, Normalizer.Form.NFD);
        String slug = LATIN.matcher(normalized).replaceAll("");

        return slug.toLowerCase(Locale.ENGLISH);
    }

    /**
     * {@inheritDoc}
     */
    public boolean predicate(int i, List<String> slugs) {
        return iterationOf(slugs.get(i)) != i;
    }

    /**
     * {@inheritDoc}
     */
    public int iterationOf(String slug) {
        int lastIndex = slug.lastIndexOf("-");

        if (lastIndex == -1) {
            return 0;
        }

        try {
            return Integer.parseInt(slug.substring(lastIndex + 1));
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    /**
     * {@inheritDoc}
     */
    public String createNew(String value, int iteration) {
        return iteration == 0 ? value : value + "-" + iteration;
    }
}
