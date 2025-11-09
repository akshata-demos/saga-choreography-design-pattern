package com.dailydose.order.service;

import com.dailydose.common.dto.OrderRequestDto;
import com.dailydose.common.event.OrderEvent;
import com.dailydose.common.event.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

@Service
public class OrderStatusPublisher {

    @Autowired
    private Sinks.Many<OrderEvent> orderSinks;

    @Lazy
    @Autowired
    private OrderStatusPublisher orderStatusPublisher;

    public void publishOrderEvent(OrderRequestDto orderRequestDto, OrderStatus orderStatus) {
        OrderEvent orderEvent = new OrderEvent(orderRequestDto, orderStatus);
        orderSinks.tryEmitNext(orderEvent);
    }


}
