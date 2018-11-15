package com.example.snackbar.snackbar.order;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Data
public class OrderDataCustom {

    String client;

    List<OrderDataCustomItem> items;

    public OrderDataCustom(String client, List<OrderDataCustomItem> items) {
        if (StringUtils.isEmpty(client)) {
            throw new IllegalArgumentException("Value can not be null or be blank.");
        }
        if (items.isEmpty()) {
            throw new IllegalArgumentException("Required at least one item.");
        }

        this.client = client;
        this.items = items;
    }
}