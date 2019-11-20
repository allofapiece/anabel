package com.pinwheel.anabel.repository.api.handler;

import com.pinwheel.anabel.entity.User;
import com.pinwheel.anabel.entity.UserSocial;
import com.pinwheel.anabel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
@RequiredArgsConstructor
public class UserEventHandler {
    private final UserRepository userRepository;

    @HandleAfterSave
    public void handleUserSave(User user) {
        syncUser(user);
    }

    @HandleAfterSave
    public void handleUserSocialSave(UserSocial userSocial) {
        syncUser();
    }

    @HandleAfterCreate
    public void handleUserSocialCreate(UserSocial userSocial) {
        syncUser();
    }

    @HandleAfterDelete
    public void handleUserSocialDelete(UserSocial userSocial) {
        syncUser();
    }

    @HandleAfterLinkDelete
    public void handleUserSocialLinkDelete(UserSocial userSocial) {
        syncUser();
    }

    private void syncUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user = userRepository.findById(user.getId()).get();

        syncUser(user);
    }

    private void syncUser(User user) {
        Authentication authentication = new PreAuthenticatedAuthenticationToken(user, user.getPassword(), user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
