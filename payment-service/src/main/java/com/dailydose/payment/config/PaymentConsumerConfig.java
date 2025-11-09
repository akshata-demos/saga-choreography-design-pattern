package com.dailydose.payment.config;


import com.dailydose.common.event.OrderEvent;
import com.dailydose.common.event.OrderStatus;
import com.dailydose.common.event.PaymentEvent;
import com.dailydose.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Configuration
public class PaymentConsumerConfig {

    @Autowired
    private PaymentService paymentService;

    @Bean
    public Function<Flux<OrderEvent>,Flux<PaymentEvent>> paymentProcessor() {
        return orderEventFlux -> orderEventFlux.flatMap(this::processPayment);
    }


    private Mono<PaymentEvent> processPayment(OrderEvent orderEvent) {
        if (OrderStatus.ORDER_CREATED.equals(orderEvent.getOrderStatus())) {
            return Mono.fromSupplier(()-> this.paymentService.newOrderEvent(orderEvent));
        } else {
            return Mono.fromRunnable(()-> this.paymentService.cancelOrderEvent(orderEvent));
        }
    }
}
