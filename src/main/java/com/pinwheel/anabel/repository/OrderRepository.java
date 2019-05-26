package com.pinwheel.anabel.repository;

import com.pinwheel.anabel.entity.Order;
import com.pinwheel.anabel.entity.Status;
import com.pinwheel.anabel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Order Repository.
 *
 * @version 1.0.0
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findOrderByIdAndStatusNot(Long id, Status status);
}
