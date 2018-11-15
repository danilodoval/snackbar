package com.example.snackbar.snackbar.order;

import com.example.snackbar.snackbar.SnackBarApplication;
import com.example.snackbar.snackbar.exception.MessageException;
import com.example.snackbar.snackbar.ingredient.Ingredient;
import com.example.snackbar.snackbar.ingredient.IngredientService;
import com.example.snackbar.snackbar.promotion.Promotion;
import com.example.snackbar.snackbar.promotion.PromotionService;
import com.example.snackbar.snackbar.snack.Snack;
import com.example.snackbar.snackbar.snack.SnackData;
import com.example.snackbar.snackbar.snack.SnackService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.example.snackbar.snackbar.utils.EnvironmentUtils.Constants.*;
import static java.util.Arrays.asList;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(SnackBarApplication.class);
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private IngredientService ingredientService;
    @Autowired
    private PromotionService promotionService;
    @Autowired
    private SnackService snackService;

    double calculeDiscount(List<OrderDataCustomItem> items, double cost) {

        boolean with = false;
        double discont = 0.0;

        List<Promotion> promotions = promotionService.getAllPromotion();
        for (Promotion promotion : promotions) {

            Ingredient ingredientWith = promotion.getWith().size() != 0 ? promotion.getWith().get(0) : null;
            Ingredient ingredientWithout = promotion.getWithout().size() != 0 ? promotion.getWithout().get(0) : null;

            if (items.stream().anyMatch(e -> e.getIngredientId().equals(ingredientWith.getId()))
                    && (ingredientWithout == null
                    || items.stream().noneMatch(e -> e.getIngredientId().equals(ingredientWithout.getId())))) {
                with = true;
                logger.info("Apply discount");
            }

            if (with) {
                int count = items.stream().filter(e -> (ingredientWith.getId().equals(e.getIngredientId())))
                        .mapToInt(x -> x.getQuantity()).findFirst().orElse(0);

                int multipleItens = count / promotion.getQuantityWith();

                logger.info("apply item discount " + promotion.isDiscountItem());
                if (promotion.isDiscountItem()) {
                    discont += (ingredientWith.getCost() * promotion.getQuantityWith()) * ((promotion.getDiscount() * multipleItens) / 100);
                } else {
                    discont += cost * ((promotion.getDiscount() * multipleItens) / 100);
                }
            }

            with = false;
        }
        return discont;
    }

    public Page<Order> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    public Boolean exists(String id) {
        return orderRepository.exists(id);
    }

    public Order findById(String id) {
        return orderRepository.findById(id);
    }

    public ResponseEntity createOrder(OrderData orderData) {

        if (StringUtils.isEmpty(orderData.getClient())) {
            throw new MessageException(SNACK_CLIENTREQUIRED, HttpStatus.BAD_REQUEST);
        }

        if (orderData.getSnacks() == null) {
            throw new MessageException(SNACK_SNACKREQUIRED, HttpStatus.BAD_REQUEST);
        }

        double cost = 0.0;
        Order order = new Order(orderData.getClient(), null, cost, 0);
        List<OrderItem> items = new ArrayList<>();

        for (SnackData snackData : orderData.getSnacks()) {

            Snack snack = snackService.findById(snackData.getId());
            if (snack == null) {
                throw new MessageException(SNACK_NOTFOUND, HttpStatus.NOT_FOUND);
            }

            OrderItem orderItem = new OrderItem(snack, snackData.getQuantity());
            items.add(orderItem);
            order.setItems(items);
            order.setCost(cost += snackService.calculateCost(snack) * snackData.getQuantity());
        }

        order = orderRepository.save(order);
        return ResponseEntity.ok(order);
    }

    public ResponseEntity createOrderCustom(OrderDataCustom orderDataCustom) {

        if (StringUtils.isEmpty(orderDataCustom.getClient())) {
            throw new MessageException(SNACK_CLIENTREQUIRED, HttpStatus.BAD_REQUEST);
        }

        if (orderDataCustom.getItems() == null) {
            throw new MessageException(SNACK_INGREDIENTREQUIRED, HttpStatus.BAD_REQUEST);
        }

        if (orderDataCustom.getItems().size() == 0) {
            throw new MessageException(SNACK_INGREDIENTREQUIRED, HttpStatus.BAD_REQUEST);
        }

        double cost = 0.0;
        double discount = 0.0;
        Order order = new Order(orderDataCustom.getClient(), null, cost, discount);
        List<Ingredient> ingredientList = new ArrayList<>();

        Snack snack = new Snack("Custom", null);

        for (OrderDataCustomItem item : orderDataCustom.getItems()) {
            Ingredient ingredient = ingredientService.findById(item.ingredientId);
            if (ingredient == null) {
                throw new MessageException(INGREDIENT_NOTFOUND, HttpStatus.NOT_FOUND);
            }

            for (int i = 0; i < item.getQuantity(); i++) {
                ingredientList.add(ingredient);
            }
            cost += ingredient.getCost() * item.getQuantity();
        }

        discount = calculeDiscount(orderDataCustom.getItems(), cost);

        snack.setIngredients(ingredientList);
        order.setItems(asList(new OrderItem(snack, 1)));

        DecimalFormat format = new DecimalFormat("#,##");
        order.setDiscount(Double.valueOf(format.format(discount)));
        order.setCost(Double.valueOf(format.format(cost - discount)));

        order = orderRepository.save(order);
        return ResponseEntity.ok(order);
    }
}
