package com.example.snackbar.snackbar.snack;

import lombok.Data;

@Data
public class SnackData {

    String id;

    Integer quantity;

    public SnackData(String id, Integer quantity) {
        this.id = id;
        this.quantity = quantity;
    }
}
