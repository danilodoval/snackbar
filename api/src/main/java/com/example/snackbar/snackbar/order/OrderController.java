package com.example.snackbar.snackbar.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/${api.version}")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllIngredients(@PageableDefault(size = 1000) Pageable pageable) {

        Page<Order> orders = orderService.getAllOrders(pageable);

        if (!orders.hasContent())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(orders);
    }

    @PostMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createOrder(@RequestBody OrderData orderData) {
        return orderService.createOrder(orderData);
    }

    @PostMapping(value = "/orders/custom", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createOrderCustom(@RequestBody OrderDataCustom orderDataCustom) {
        return orderService.createOrderCustom(orderDataCustom);
    }
}