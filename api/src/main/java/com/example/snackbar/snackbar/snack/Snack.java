package com.example.snackbar.snackbar.snack;

import com.example.snackbar.snackbar.ingredient.Ingredient;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
public class Snack {

    @Id
    String id;

    String description;

    List<Ingredient> ingredients;

    public Snack(String description, List<Ingredient> ingredients) {
        if (StringUtils.isEmpty(description)) {
            throw new IllegalArgumentException("Value can not be null or be blank.");
        }
        if (ingredients.isEmpty()) {
            throw new IllegalArgumentException("Required at least one item.");
        }

        this.description = description;
        this.ingredients = ingredients;
    }

    public boolean isEmpty() {
        return StringUtils.isEmpty(description) && ingredients.isEmpty();
    }
}
