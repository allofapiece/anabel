package com.pinwheel.anabel.service;

import com.pinwheel.anabel.entity.User;
import com.pinwheel.anabel.entity.VerificationToken;

/**
 * Verification token service. Contains general logic for verification token. Verification token used for verifying
 * user after his registration.
 *
 * @version 1.0.0
 */
public interface VerificationTokenService {
    /**
     * Create verification token for user.
     *
     * @param user target user.
     * @return ready-state and saved verification token.
     */
    VerificationToken create(User user);

    /**
     * Creates verification token for user by provided token string.
     *
     * @param user  target user.
     * @param token provided token string.
     * @return ready-state and saved verification token.
     */
    VerificationToken create(User user, String token);

    /**
     * Generates new verification token string.
     *
     * @return generated token string.
     */
    String generate();

    /**
     * Checks whether verification token is expired.
     *
     * @param verificationToken verification token for checking.
     * @return whether verification token is expired.
     */
    boolean isExpired(VerificationToken verificationToken);

    /**
     * Sets passed verification token as expired.
     *
     * @param verificationToken verification token.
     */
    void reject(VerificationToken verificationToken);

    /**
     * Finds token entity by verification token string.
     *
     * @param token verification token string.
     * @return verification token entity.
     */
    VerificationToken findByToken(String token);
}
