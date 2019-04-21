package com.pinwheel.anabel.service;

import com.pinwheel.anabel.entity.User;
import com.pinwheel.anabel.entity.VerificationToken;

/**
 * @author Listratenko Stanislav
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

    boolean isExpired(VerificationToken verificationToken);

    void reject(VerificationToken verificationToken);

    VerificationToken findByToken(String token);
}
