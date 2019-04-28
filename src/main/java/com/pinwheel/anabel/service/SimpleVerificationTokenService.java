package com.pinwheel.anabel.service;

import com.pinwheel.anabel.entity.User;
import com.pinwheel.anabel.entity.VerificationToken;
import com.pinwheel.anabel.repository.VerificationTokenRepository;
import com.pinwheel.anabel.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Simple verification token service. Contains maintain logic for {@link VerificationToken} entity. Implements
 * {@link VerificationTokenService} interface.
 *
 * @version 1.0.0
 */
@RequiredArgsConstructor
@Service
public class SimpleVerificationTokenService implements VerificationTokenService {
    /**
     * Life time of the verification token in minutes.
     */
    @Value("#{new Integer('${application.token.verification.expiration}')}")
    private int expiration;

    /**
     * Injection of {@link VerificationTokenRepository} bean.
     */
    private final VerificationTokenRepository verificationTokenRepository;

    /**
     * {@inheritDoc}
     */
    public VerificationToken create(User user) {
        return create(user, generate());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VerificationToken create(User user, String token) {
        user.getVerificationTokens().forEach(verificationToken -> {
            verificationToken.setExpire(new Date());
            verificationTokenRepository.save(verificationToken);
        });

        final VerificationToken verificationToken = VerificationToken.builder()
                .user(user)
                .token(token)
                .expire(DateUtils.expire(this.expiration))
                .build();

        user.addVerificationToken(verificationToken);

        return verificationTokenRepository.save(verificationToken);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isExpired(VerificationToken verificationToken) {
        if (verificationToken == null) {
            return true;
        }

        final Calendar cal = Calendar.getInstance();

        return (verificationToken.getExpire().getTime() - cal.getTime().getTime()) <= 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reject(VerificationToken verificationToken) {
        verificationToken.setExpire(new Date());
        verificationTokenRepository.save(verificationToken);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VerificationToken findByToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }
}
