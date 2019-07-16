package com.pinwheel.anabel.service;

import java.util.List;
import java.util.function.Function;

/**
 * Slug generator. Contains logic for slugs generation. Generates unique slugs, according on provided {@link Function},
 * which must return list of slugs looks like target.
 *
 * @version 1.0.0
 */
public interface SlugGenerator {
    /**
     * Generates unique slugs, according on provided {@link Function},
     * which must return list of slugs looks like {@code target}.
     *
     * @param target                target string for generation of slug.
     * @param existentSlugsCallback {@link Function}, which must return list of slugs looks like {@code target}.
     * @return generated slug.
     */
    String slug(String target, Function<String, List<String>> existentSlugsCallback);

    /**
     * Filters slug.
     *
     * @param target target for filtering.
     * @return filtered string.
     */
    String filterString(String target);

    /**
     * Returns number of passed slug. For example:
     * <pre>
     * {@code
     * int iterationOf("slug-4") // 4
     * int iterationOf("slug-32") // 32
     * int iterationOf("slug") // 0
     * int iterationOf("slug-14-2") // 2
     * }
     * </pre>
     *
     * @param target target for calculating of iteration.
     * @return number of slug.
     */
    int iterationOf(String target);

    /**
     * Creates new slug according on passed {@code iteration}.For example:
     * <pre>
     * {@code
     * int createNew("slug", 3) // "slug-3"
     * int iterationOf("slug", 0) // "slug"
     * int iterationOf("slug-14", 2) // "slug-14-2"
     * }
     * </pre>
     *
     * @param value     string, that will be concatenated with iteration.
     * @param iteration iteration of the slug.
     * @return created slug.
     */
    String createNew(String value, int iteration);

    /**
     * Predicate method for calculating place for new slug.
     *
     * @param i     current index of stream iteration.
     * @param slugs list of all existent slug looks like target.
     * @return whether result of {@link SlugGenerator#iterationOf(String)} equals to passed {@code i}.
     */
    boolean predicate(int i, List<String> slugs);
}
