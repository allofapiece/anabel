package com.pinwheel.anabel.repository;

import com.pinwheel.anabel.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;

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
