package com.example.snackbar.snackbar.snack;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.snackbar.snackbar.ingredient.Ingredient;

import lombok.Data;

@Data
@Document
public class Snack {

    @Id
    String id;

    String description;

    List<Ingredient> ingredients;

    public Snack(String description, List<Ingredient> ingredients) {
        this.description = description;
        this.ingredients = ingredients;
    }
}
