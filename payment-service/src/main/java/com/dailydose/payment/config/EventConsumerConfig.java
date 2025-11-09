package com.dailydose.payment.config;

import com.dailydose.common.event.PaymentEvent;
import com.dailydose.order.config.OrderStatusUpdateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.function.Consumer;

@Configuration
public class EventConsumerConfig {

    @Lazy
    @Autowired
    private OrderStatusUpdateHandler orderStatusUpdateHandler;

    public Consumer<PaymentEvent> paymentEvent_Consumer() {
        return (paymentEvent)-> orderStatusUpdateHandler.updateOrder(paymentEvent.getPaymentRequestDto().getOrderId(),
                paymentOrder->{
                    paymentOrder.setPaymentStatus(paymentEvent.getPaymentStatus());
                });
    }
}
