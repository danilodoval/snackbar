package com.example.snackbar.snackbar.order;

import lombok.Data;

@Data
public class OrderDataCustomItem {

    String ingredientId;

    int quantity;

    public OrderDataCustomItem(String ingredientId, int quantity) {
        this.ingredientId = ingredientId;
        this.quantity = quantity;
    }
}
