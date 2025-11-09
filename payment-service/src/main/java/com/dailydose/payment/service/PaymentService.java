package com.dailydose.payment.service;

import com.dailydose.common.dto.OrderRequestDto;
import com.dailydose.common.dto.PaymentRequestDto;
import com.dailydose.common.event.OrderEvent;
import com.dailydose.common.event.PaymentEvent;
import com.dailydose.common.event.PaymentStatus;
import com.dailydose.payment.repository.UserBalanceRepository;
import com.dailydose.payment.entity.UserBalance;
import com.dailydose.payment.entity.UserTransaction;
import com.dailydose.payment.repository.UserTransactionRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PaymentService {


    @Autowired
    private UserBalanceRepository userBalanceRepository;

    @Autowired
    private UserTransactionRepository userTransactionRepository;

    @PostConstruct
    public void initUserBalanceInDB() {
        userBalanceRepository.saveAll(Stream.of(new UserBalance(101, 5000),
                new UserBalance(102, 6000),
                new UserBalance(103, 7000),
                new UserBalance(104, 8000),
                new UserBalance(105, 9000),
                new UserBalance(106, 10000)).collect(Collectors.toList()));
    }

    @Transactional
    public PaymentEvent newOrderEvent(OrderEvent orderEvent) {
        OrderRequestDto orderRequestDto = orderEvent.getOrderRequestDto();
        PaymentRequestDto paymentRequestDto = new PaymentRequestDto(orderRequestDto.getOrderId(), orderRequestDto.getUserId(), orderRequestDto.getAmount());

        return userBalanceRepository.findById(orderRequestDto.getUserId())
                .filter(ub -> ub.getPrice() > orderRequestDto.getAmount())
                .map(ub -> {
                    ub.setPrice(ub.getPrice() - orderRequestDto.getAmount());
                    userTransactionRepository.save(new UserTransaction(orderRequestDto.getOrderId(),
                            orderRequestDto.getUserId(), orderRequestDto.getAmount()));
                    return new PaymentEvent(paymentRequestDto, PaymentStatus.PAYMENT_COMPLETED);
                }).orElse(new PaymentEvent(paymentRequestDto, PaymentStatus.PAYMENT_FAILED));
    }

    @Transactional
    public void cancelOrderEvent(OrderEvent orderEvent) {
        userTransactionRepository.findById(orderEvent.getOrderRequestDto().getOrderId())
                .ifPresent(ut->{
                    userTransactionRepository.delete(ut);
                    userTransactionRepository.findById(ut.getUserId())
                            .ifPresent(ub->ub.setAmount(ub.getAmount()+ ub.getAmount()));
                });
    }
}