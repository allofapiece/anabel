package com.pinwheel.anabel.external.config;

import com.pinwheel.anabel.entity.Password;
import com.pinwheel.anabel.entity.Role;
import com.pinwheel.anabel.entity.User;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Collections;
import java.util.Set;

/**
 * @version 1.0.0
 */
@TestConfiguration
public class SpringSecurityTestConfig {

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        User basicUser = new User();
        basicUser.setEmail("mike@gmail.com");
        basicUser.setDisplayName("Mike");
        basicUser.setPassword(new Password("Qqqq1111"));
        basicUser.setRoles(Set.of(Role.ADMIN, Role.USER));

        return new InMemoryUserDetailsManager(Collections.singletonList(
                basicUser
        ));
    }
}
