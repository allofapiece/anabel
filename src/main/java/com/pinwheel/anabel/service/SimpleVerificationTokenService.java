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
 * @author Listratenko Stanislav
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
        final VerificationToken verificationToken = VerificationToken.builder()
                .user(user)
                .token(token)
                .expire(DateUtils.expire(this.expiration))
                .build();

        user.addVerificationToken(verificationToken);

        return verificationTokenRepository.save(new VerificationToken());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }

    @Override
    public boolean isExpired(VerificationToken verificationToken) {
        if (verificationToken == null) {
            return true;
        }

        final Calendar cal = Calendar.getInstance();

        return (verificationToken.getExpire().getTime() - cal.getTime().getTime()) <= 0;
    }

    @Override
    public void reject(VerificationToken verificationToken) {
        verificationToken.setExpire(new Date());
        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public VerificationToken findByToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }
}
