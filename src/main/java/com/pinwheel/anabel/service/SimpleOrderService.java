package com.pinwheel.anabel.service;

import com.pinwheel.anabel.entity.*;
import com.pinwheel.anabel.event.OnRegistrationCompleteEvent;
import com.pinwheel.anabel.repository.OrderRepository;
import com.pinwheel.anabel.repository.UserRepository;
import com.pinwheel.anabel.service.notification.NotificationService;
import com.pinwheel.anabel.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * User service. Provides logic for users.
 *
 * @version 1.0.0
 */
@RequiredArgsConstructor
@Service
@Transactional
public class SimpleOrderService implements OrderService {
    /**
     * Injection of {@link OrderRepository} bean.
     */
    private final OrderRepository orderRepository;

    /**
     * Injection of {@link ApplicationEventPublisher} bean.
     */
    private final ApplicationEventPublisher eventPublisher;

    private final NotificationService notificationService;

    @Override
    public Order save(Order order) {
        User user = SecurityUtils.principal();

        order.setUser(user);
        order.setStatus(Status.ACTIVE);

        return orderRepository.save(order);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> findOwn() {
        return orderRepository.findOrderByIdAndStatusNot(SecurityUtils.principal().getId(), Status.DELETED);
    }
}
