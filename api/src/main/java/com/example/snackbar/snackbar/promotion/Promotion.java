package com.example.snackbar.snackbar.promotion;

import com.example.snackbar.snackbar.ingredient.Ingredient;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
public class Promotion {

    @Id
    String id;

    String description;

    List<Ingredient> with;

    List<Ingredient> without;

    int quantityWith;

    double discount;

    boolean discountItem;

    public Promotion(String description, List<Ingredient> with, List<Ingredient> without, int quantityWith, double discount, boolean discountItem) {
        if (StringUtils.isEmpty(description)) {
            throw new IllegalArgumentException("Value can not be null or be blank.");
        }
        if (with.isEmpty()) {
            throw new IllegalArgumentException("Required at least one item.");
        }
        if (quantityWith <= 0 || discount < 0) {
            throw new ValueException("Value can not be negative.");
        }
        this.description = description;
        this.with = with;
        this.without = without;
        this.quantityWith = quantityWith;
        this.discount = discount;
        this.discountItem = discountItem;
    }
}
