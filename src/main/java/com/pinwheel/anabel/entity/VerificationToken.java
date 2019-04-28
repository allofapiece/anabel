package com.pinwheel.anabel.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * Token for user verification. Determines whether user verified his account.
 *
 * @author Listratenko Stanislav
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class VerificationToken {
    /**
     * Constructor for default token string.
     *
     * @param token token string.
     */
    public VerificationToken(String token) {
        this.token = token;
    }

    /**
     * Entity primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Unique string, which sending to user email for verification.
     */
    private String token;

    /**
     * Relative user entity.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Represents date of token expiration.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date expire;
}
