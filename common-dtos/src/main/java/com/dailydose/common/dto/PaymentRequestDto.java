package com.dailydose.common.dto;

import jakarta.persistence.Entity;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDto {

    private Integer orderId;
    private Integer userId;
    private Integer amount;


}
