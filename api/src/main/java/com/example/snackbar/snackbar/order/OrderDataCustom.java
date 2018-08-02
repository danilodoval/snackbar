package com.example.snackbar.snackbar.order;

import lombok.Data;

import java.util.List;

@Data
public class OrderDataCustom {

    String client;

    List<OrderDataCustomItem> items;

    public OrderDataCustom(String client, List<OrderDataCustomItem> items) {
        this.client = client;
        this.items = items;
    }
}