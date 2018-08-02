package com.example.snackbar.snackbar.order;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

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
        this.client = client;
        this.items = items;
        this.cost = cost;
        this.discount = discount;
    }
}
