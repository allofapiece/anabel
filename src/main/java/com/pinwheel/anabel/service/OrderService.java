package com.pinwheel.anabel.service;

import com.pinwheel.anabel.entity.Order;

import java.util.List;

/**
 * @version 1.0.0
 */
public interface OrderService {
    Order save(Order order);

    List<Order> findAll();

    List<Order> findOwn();
}
