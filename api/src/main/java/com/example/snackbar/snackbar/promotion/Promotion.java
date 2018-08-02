package com.example.snackbar.snackbar.promotion;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.snackbar.snackbar.ingredient.Ingredient;

import lombok.Data;

@Data
@Document
public class Promotion {

    @Id
    String id;

    String description;

    List<Ingredient> with;

    List<Ingredient> without;

    int quantitywith;

    double discount;

    boolean discountItem;

    public Promotion(String description, List<Ingredient> with, List<Ingredient> without, int quantitywith, double discount, boolean discountItem) {
        this.description = description;
        this.with = with;
        this.without = without;
        this.quantitywith = quantitywith;
        this.discount = discount;
        this.discountItem = discountItem;
    }
}
