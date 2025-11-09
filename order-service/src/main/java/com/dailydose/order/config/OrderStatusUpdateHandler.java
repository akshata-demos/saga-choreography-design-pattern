package com.dailydose.order.config;

import com.dailydose.common.dto.OrderRequestDto;
import com.dailydose.common.event.OrderStatus;
import com.dailydose.common.event.PaymentStatus;
import com.dailydose.order.entity.Purchase_Order;
import com.dailydose.order.repository.OrderRepository;
import com.dailydose.order.service.OrderStatusPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;

@Configuration
public class OrderStatusUpdateHandler {

    @Autowired
    private OrderRepository orderRepository;

    @Lazy
    @Autowired
    private OrderStatusPublisher orderStatusPublisher;

    @Transactional
    public void updateOrder(int id, Consumer<Purchase_Order> consumer) {
        orderRepository.findById(id).ifPresent(consumer.andThen(this::updateOrder));
    }

    private void updateOrder(Purchase_Order purchaseOrder) {
        boolean isPaymentComplete = PaymentStatus.PAYMENT_COMPLETED.equals(purchaseOrder.getPaymentStatus());
        OrderStatus orderStatus = isPaymentComplete? OrderStatus.ORDER_COMPLETED:OrderStatus.ORDER_CANCELLED;
        purchaseOrder.setOrderStatus(orderStatus);
        if (!isPaymentComplete) {
            orderStatusPublisher.publishOrderEvent(convertEntityToDto(purchaseOrder),orderStatus);
        }
    }

    public OrderRequestDto convertEntityToDto(Purchase_Order purchaseOrder) {
        OrderRequestDto orderRequestDto = new OrderRequestDto();
        orderRequestDto.setOrderId(purchaseOrder.getPurchaseOrderId());
        orderRequestDto.setUserId(purchaseOrder.getUserId());
        orderRequestDto.setAmount(purchaseOrder.getPrice());
        orderRequestDto.setProductId(purchaseOrder.getProductId());
        return orderRequestDto;
    }
}
