package com.example.snackbar.snackbar.order;

import java.util.List;

import com.example.snackbar.snackbar.snack.SnackData;

import lombok.Data;

@Data
public class OrderData {

    String client;

    List<SnackData> snacks;

    public OrderData(String client, List<SnackData> snacks) {
        this.client = client;
        this.snacks = snacks;
    }
}
