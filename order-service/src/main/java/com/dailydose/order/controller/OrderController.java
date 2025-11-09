package com.dailydose.order.controller;

import com.dailydose.common.dto.OrderRequestDto;
import com.dailydose.order.entity.Purchase_Order;
import com.dailydose.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public Purchase_Order createOrder(@RequestBody OrderRequestDto orderRequestDto) {
       return orderService.createOrder(orderRequestDto);
    }

    @GetMapping("/getOrders")
    public List<Purchase_Order> getAllOrders() {
        return orderService.getOrders();
    }
}
