package com.example.snackbar.snackbar.order;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class OrderDataCustomItem {

    String ingredientId;

    int quantity;

    public OrderDataCustomItem(String ingredientId, int quantity) {
        if (StringUtils.isEmpty(ingredientId)) {
            throw new IllegalArgumentException("Value can not be null or be blank.");
        }
        if (quantity <= 0) {
            throw new ValueException("Value can not be negative.");
        }

        this.ingredientId = ingredientId;
        this.quantity = quantity;
    }
}
