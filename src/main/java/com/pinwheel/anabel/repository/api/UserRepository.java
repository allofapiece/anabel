package com.pinwheel.anabel.repository.api;

import com.pinwheel.anabel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;


@RepositoryRestResource(collectionResourceRel = "users", path = "users")
@Component("userApiRepository")
public interface UserRepository extends JpaRepository<User, Long> {
    @Override
    @PreAuthorize("hasAuthority('ADMIN') || (!hasAuthority('ANONYMOUS') && authentication.principal.id == #entity.id)")
    <S extends User> S save(S entity);
}
