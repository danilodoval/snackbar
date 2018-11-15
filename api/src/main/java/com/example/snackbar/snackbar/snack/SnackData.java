package com.example.snackbar.snackbar.snack;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class SnackData {

    String id;

    Integer quantity;

    public SnackData(String id, Integer quantity) {
        if (StringUtils.isEmpty(id)) {
            throw new IllegalArgumentException("Value can not be null or be blank.");
        }
        if (quantity <= 0) {
            throw new ValueException("Value can not be negative.");
        }

        this.id = id;
        this.quantity = quantity;
    }
}
