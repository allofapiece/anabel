package com.pinwheel.anabel.repository;

import com.pinwheel.anabel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User Repository.
 *
 * @author Listratenko Stanislav
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
     * Finds user by confirmation code.
     *
     * @param confirmationCode confirmation code.
     * @return found user entity.
     */
    User findByConfirmationCode(String confirmationCode);
}
