package com.example.snackbar.snackbar.order;

import com.example.snackbar.snackbar.snack.Snack;

import lombok.Data;

@Data
public class OrderItem {

    Snack snack;

    Integer quantity;

    public OrderItem(Snack snack, Integer quantity) {
        this.snack = snack;
        this.quantity = quantity;
    }
}
