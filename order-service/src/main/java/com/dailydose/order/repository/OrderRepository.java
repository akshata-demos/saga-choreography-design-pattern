package com.dailydose.order.repository;

import com.dailydose.order.entity.Purchase_Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Purchase_Order, Integer> {



}
