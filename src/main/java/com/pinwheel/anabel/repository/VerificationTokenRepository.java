package com.pinwheel.anabel.repository;

import com.pinwheel.anabel.entity.User;
import com.pinwheel.anabel.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.stream.Stream;

/**
 * Verification Token Repository.
 *
 * @author Listratenko Stanislav
 * @version 1.0.0
 */
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    /**
     * Finds {@link VerificationToken} entity by provided token string.
     *
     * @param token token string.
     * @return found verification token entity.
     */
    VerificationToken findByToken(String token);

    /**
     * Finds {@link VerificationToken verification tokens} by provided user entity.
     *
     * @param user user entity.
     * @return found verification token entity.
     */
    VerificationToken findByUser(User user);

    /**
     * Finds all expired verification tokens.
     *
     * @param now current date.
     * @return stream of verification tokens.
     */
    Stream<VerificationToken> findAllByExpireLessThan(Date now);

    /**
     * Deletes all expired verification tokens.
     *
     * @param now current date.
     */
    void deleteByExpireLessThan(Date now);

    @Modifying
    @Query("delete from VerificationToken t where t.expire <= ?1")
    void deleteAllExpiredSince(Date now);
}
