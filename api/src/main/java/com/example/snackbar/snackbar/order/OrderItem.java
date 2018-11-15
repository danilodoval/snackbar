package com.example.snackbar.snackbar.order;

import com.example.snackbar.snackbar.snack.Snack;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class OrderItem {

    Snack snack;

    Integer quantity;

    public OrderItem(Snack snack, Integer quantity) {
        if (snack.isEmpty()) {
            throw new IllegalArgumentException("Value can not be null or be blank.");
        }
        if (quantity <= 0) {
            throw new ValueException("Value can not be negative.");
        }

        this.snack = snack;
        this.quantity = quantity;
    }
}
