package com.pinwheel.anabel.entity;

import org.springframework.data.rest.core.config.Projection;

/**
 * TODO make registering projection beans by configuration for moving projections to another package. https://www.baeldung.com/spring-data-rest-projections-excerpts
 */
@Projection(name = "withSocialNetwork", types = {UserSocial.class})
public interface UserSocialWithSocialNetworkProjection {
    Long getId();

    String getLink();

    SocialNetwork getSocialNetwork();
}
