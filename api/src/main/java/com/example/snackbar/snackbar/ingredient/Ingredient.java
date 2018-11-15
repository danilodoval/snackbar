package com.example.snackbar.snackbar.ingredient;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Ingredient {

    @Id
    String id;

    String description;

    double cost;

    public Ingredient(String description, double cost) {
        if (StringUtils.isEmpty(description)) {
            throw new IllegalArgumentException("Value can not be null or be blank.");
        }
        if (cost < 0) {
            throw new ValueException("Value can not be negative.");
        }
        this.description = description;
        this.cost = cost;
    }

    public Ingredient(String id, String description, double cost) {
        if (StringUtils.isEmpty(id) || StringUtils.isEmpty(description)) {
            throw new IllegalArgumentException("Value can not be null or be blank.");
        }
        if (cost < 0) {
            throw new ValueException("Value can not be negative.");
        }
        this.id = id;
        this.description = description;
        this.cost = cost;
    }
}
