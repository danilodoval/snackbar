package com.example.snackbar.snackbar.order;

import com.example.snackbar.snackbar.snack.SnackData;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Data
public class OrderData {

    String client;

    List<SnackData> snacks;

    public OrderData(String client, List<SnackData> snacks) {
        if (StringUtils.isEmpty(client)) {
            throw new IllegalArgumentException("Value can not be null or be blank.");
        }
        if (snacks.isEmpty()) {
            throw new IllegalArgumentException("Required at least one item.");
        }

        this.client = client;
        this.snacks = snacks;
    }
}
