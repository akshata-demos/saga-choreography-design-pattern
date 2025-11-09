package com.dailydose.payment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class UserBalance {

    @Id
    private int userId;
    private int price;

    public UserBalance(int userId, int price) {
        this.userId = userId;
        this.price = price;
    }
}
