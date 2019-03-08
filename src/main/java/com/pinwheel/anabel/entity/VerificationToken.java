package com.pinwheel.anabel.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Token for user verification. Determines whether user verified his account.
 *
 * @author Listratenko Stanislav
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@Entity
public class VerificationToken {
    /**
     * Determines living time of the token in minutes.
     */
    private static final int EXPIRATION = 60 * 24;

    /**
     * Entity primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Unique string, which sending to user email for verification.
     */
    private String token;

    /**
     * Relative user entity.
     */
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    /**
     * Represents date of token expiration.
     */
    private Date expiryDate;

    /**
     * Calculates expiration data for writing in database.
     *
     * @param expiryTime time in minutes representing expiration time by now.
     * @return Date of expiration.
     */
    private Date calculateExpiryDate(int expiryTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTime);

        return new Date(cal.getTime().getTime());
    }
}
