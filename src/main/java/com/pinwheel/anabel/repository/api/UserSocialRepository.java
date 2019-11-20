package com.pinwheel.anabel.repository.api;

import com.fasterxml.jackson.annotation.JsonView;
import com.pinwheel.anabel.entity.UserSocial;
import com.pinwheel.anabel.entity.Views;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;


@RepositoryRestResource(collectionResourceRel = "user-socials", path = "user-socials")
@Component
@JsonView(Views.WithGeneral.class)
public interface UserSocialRepository extends JpaRepository<UserSocial, Long> {
    UserSocial findByUserId(Long id);

    @Override
    @PreAuthorize("hasAuthority('USER') && #entity.user.id == authentication.principal.id")
    void delete(UserSocial entity);
}
