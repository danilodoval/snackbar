package com.example.snackbar.snackbar.order;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
public class Order {

    @Id
    String id;

    @Indexed
    String client;

    List<OrderItem> items;

    double cost;

    double discount;

    public Order(String client, List<OrderItem> items, double cost, double discount) {
        if (StringUtils.isEmpty(client)) {
            throw new IllegalArgumentException("Value can not be null or be blank.");
        }
        if (items.isEmpty()) {
            throw new IllegalArgumentException("Required at least one item.");
        }
        if (cost < 0 || discount < 0) {
            throw new ValueException("Value can not be negative.");
        }

        this.client = client;
        this.items = items;
        this.cost = cost;
        this.discount = discount;
    }
}
