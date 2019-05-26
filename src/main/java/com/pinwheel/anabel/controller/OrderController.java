package com.pinwheel.anabel.controller;

import com.pinwheel.anabel.entity.Order;
import com.pinwheel.anabel.entity.dto.OrderDto;
import com.pinwheel.anabel.service.CaptchaService;
import com.pinwheel.anabel.service.OrderService;
import com.pinwheel.anabel.service.UserService;
import com.pinwheel.anabel.service.notification.FlushNotificationMessageFactory;
import com.pinwheel.anabel.service.notification.NotificationService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * Describes logic of authentication, registration and etc. Generalizes logic of application security.
 *
 * @version 1.0.0
 */
@RequiredArgsConstructor
@Controller
@RequestMapping("/orders")
public class OrderController {

    /**
     * Injection of {@link ModelMapper} bean.
     */
    private final ModelMapper modelMapper;

    /**
     * Injection of {@link NotificationService} bean.
     */
    private final NotificationService notificationService;

    /**
     * Inject of {@link FlushNotificationMessageFactory} bean.
     */
    private final FlushNotificationMessageFactory flushNotificationMessageFactory;
    /**
     * Injection of the user service.
     */
    private final OrderService orderService;

    @GetMapping
    public String orders(Model model) {
        model.addAttribute("orders", orderService.findAll());
        return "order/all";
    }

    @GetMapping("/own")
    public String ownOrders(Model model) {
        model.addAttribute("orders", orderService.findOwn());
        return "order/own";
    }

    @GetMapping("/new")
    public String addOrder(Model model) {
        model.addAttribute("orderDto", new OrderDto());
        return "order/add";
    }

    @PostMapping("/new")
    public String addOrder(@Valid OrderDto orderDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "order/add";
        }

        Order order = orderService.save(modelMapper.map(orderDto, Order.class));

        if (order.getId() == null) {

        }

        return "redirect:/orders/own";
    }
}
