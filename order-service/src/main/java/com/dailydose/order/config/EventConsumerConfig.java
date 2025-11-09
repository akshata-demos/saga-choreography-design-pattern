package com.dailydose.order.config;

import com.dailydose.common.event.PaymentEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.function.Consumer;

@Configuration
public class EventConsumerConfig {


    @Autowired
    private OrderStatusUpdateHandler orderStatusUpdateHandler;

    @Bean
    public Consumer<PaymentEvent> paymentEvent_Consumer() {
        //listen payment-event-topic
        //will check payment status
        //if payment status completed -> complete the order
        //if payment status failed -> cancel the order
        return (paymentEvent) -> orderStatusUpdateHandler.updateOrder(paymentEvent.getPaymentRequestDto().getOrderId(),
                paymentOrder -> {
                    paymentOrder.setPaymentStatus(paymentEvent.getPaymentStatus());
                });
    }
}
