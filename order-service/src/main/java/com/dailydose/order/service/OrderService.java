package com.dailydose.order.service;

import com.dailydose.common.dto.OrderRequestDto;
import com.dailydose.order.entity.Purchase_Order;
import com.dailydose.common.event.OrderStatus;
import com.dailydose.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderStatusPublisher orderStatusPublisher;

    @Transactional
    public Purchase_Order createOrder(OrderRequestDto orderRequestDto) {
        Purchase_Order purchaseOrder = orderRepository.save(convertDtoToEntity(orderRequestDto));
        orderRequestDto.setOrderId(purchaseOrder.getPurchaseOrderId());

        orderStatusPublisher.publishOrderEvent(orderRequestDto,OrderStatus.ORDER_CREATED);
        return purchaseOrder;
    }

    public List<Purchase_Order> getOrders() {
        return orderRepository.findAll();
    }

    private Purchase_Order convertDtoToEntity(OrderRequestDto orderRequestDto) {
        Purchase_Order purchaseOrder = new Purchase_Order();
        purchaseOrder.setPurchaseOrderId(orderRequestDto.getProductId());
        purchaseOrder.setUserId(orderRequestDto.getUserId());
        purchaseOrder.setOrderStatus(OrderStatus.ORDER_CREATED);
        purchaseOrder.setPrice(orderRequestDto.getAmount());
        return purchaseOrder;
    }

}
