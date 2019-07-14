package com.pinwheel.anabel.repository;

import com.pinwheel.anabel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


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
}
