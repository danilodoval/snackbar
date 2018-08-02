package com.example.snackbar.snackbar.ingredient;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Ingredient {

    @Id
    String id;

    String description;

    double cost;

    public Ingredient(String description, double cost) {
        this.description = description;
        this.cost = cost;
    }

    public Ingredient(String id, String description, double cost) {
        this.id = id;
        this.description = description;
        this.cost = cost;
    }
}
