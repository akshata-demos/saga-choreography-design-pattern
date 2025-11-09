package com.dailydose.common.event;

import com.dailydose.common.dto.PaymentRequestDto;
import lombok.*;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class PaymentEvent implements Event{

    private UUID eventId = UUID.randomUUID();
    private Date eventDate = new Date();
    private PaymentRequestDto paymentRequestDto;
    private PaymentStatus paymentStatus;

    public PaymentEvent(PaymentRequestDto paymentRequestDto, PaymentStatus paymentStatus) {
        this.paymentRequestDto = paymentRequestDto;
        this.paymentStatus = paymentStatus;
    }

    @Override
    public UUID getEventId() {
        return eventId;
    }

    @Override
    public Date getDate() {
        return eventDate;
    }


}
