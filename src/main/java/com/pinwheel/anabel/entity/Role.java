package com.pinwheel.anabel.entity;

import org.springframework.security.core.GrantedAuthority;

/**
 * Role enum. Describes granted authority of the users.
 *
 * @author Listratenko Stanislav
 * @version 1.0.0
 */
public enum Role implements GrantedAuthority {
    /**
     * Default role, that assigned when user registered.
     */
    USER,

    /**
     * Admin role.
     */
    ADMIN;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAuthority() {
        return name();
    }
}
