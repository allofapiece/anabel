package com.pinwheel.anabel.repository.api;

import com.pinwheel.anabel.entity.SocialNetwork;
import com.pinwheel.anabel.entity.UserSocial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;


@RepositoryRestResource(collectionResourceRel = "socials", path = "socials")
@Component
public interface SocialNetworkRepository extends JpaRepository<SocialNetwork, Long> {
}
