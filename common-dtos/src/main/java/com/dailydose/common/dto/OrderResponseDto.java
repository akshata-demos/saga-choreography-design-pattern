package com.dailydose.common.dto;

import com.dailydose.common.event.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {

    private Integer orderId;
    private Integer userId;
    private Integer productId;
    private Integer amount;
    private OrderStatus orderStatus;

}
