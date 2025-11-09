package com.dailydose.order.entity;

//import com.dailydose.common.event.OrderStatus;
//import com.dailydose.common.event.PaymentStatus;
import com.dailydose.common.event.OrderStatus;
import com.dailydose.common.event.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "PURCHASE_ORDER_TBL")
public class Purchase_Order {

    @Id
    @GeneratedValue
    private Integer purchaseOrderId;
    private Integer userId;
    private Integer productId;
    private Integer price;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

}
