package com.pinwheel.anabel.repository;

import com.pinwheel.anabel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * User Repository.
 *
 * @version 1.0.0
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Finds user entity by provided email.
     *
     * @param email target email.
     * @return found user entity.
     */
    User findByEmail(String email);

    /**
     * Returns user by passed slug.
     *
     * @param slug user slug.
     * @return user.
     */
    User findBySlug(String slug);

    /**
     * Finds slugs, which look like as passed slug via regular expression.
     *
     * @param slug slug to finding.
     * @return list of slugs, which look like as passed.
     */
    @Query(nativeQuery = true, value = "select slug from user where slug regexp concat(:slug, '(-\\d+)*$')")
    List<String> findSlugsBySlugRegexp(@Param("slug") String slug);

    boolean existsBySlug(String slug);

    boolean existsBySlugAndIdNot(String slug, Long id);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);
}
