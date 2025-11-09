package com.dailydose.order.config;

import com.dailydose.common.event.OrderEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Supplier;

@Configuration
public class OrderPublisherConfig {

    @Bean
    public Sinks.Many<OrderEvent> orderEvent() {
        return Sinks.many().multicast().onBackpressureBuffer();
    }

    @Bean
    public Supplier<Flux<OrderEvent>> order_Supplier(Sinks.Many<OrderEvent> sinks) {
        return sinks::asFlux;
    }
}
