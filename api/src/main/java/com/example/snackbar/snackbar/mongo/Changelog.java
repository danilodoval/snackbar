package com.example.snackbar.snackbar.mongo;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.example.snackbar.snackbar.ingredient.Ingredient;
import com.example.snackbar.snackbar.promotion.Promotion;
import com.example.snackbar.snackbar.snack.Snack;
import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;

@ChangeLog
public class Changelog {

    @ChangeSet(order = "001", id = "addIngredients", author = "ddval")
    public void addIngredients(MongoTemplate mongoTemplate) {

        Ingredient ingredient = new Ingredient("Alface", 0.4);
        mongoTemplate.save(ingredient);

        ingredient = new Ingredient("Bacon", 2.0);
        mongoTemplate.save(ingredient);

        ingredient = new Ingredient("Hambúrguer de carne", 3.0);
        mongoTemplate.save(ingredient);

        ingredient = new Ingredient("Ovo", 0.8);
        mongoTemplate.save(ingredient);

        ingredient = new Ingredient("Queijo", 1.5);
        mongoTemplate.save(ingredient);
    }

    @ChangeSet(order = "002", id = "addSnacks", author = "ddval")
    public void addSnacks(MongoTemplate mongoTemplate) {

        final List<String> listItemsXBacon = asList("Bacon", "Hambúrguer de carne", "Queijo");
        List<Ingredient> ingredientList = mongoTemplate.findAll(Ingredient.class)
                .stream()
                .filter(e -> (listItemsXBacon.stream().map(String::toUpperCase).collect(Collectors.toList())).contains(e.getDescription().toUpperCase()))
                .collect(Collectors.toList());

        Snack snack = new Snack("X-Bacon", ingredientList);
        mongoTemplate.save(snack);
        ingredientList.clear();


        final List<String> listItemsXBurger = asList("Hambúrguer de carne", "Queijo");
        ingredientList = mongoTemplate.findAll(Ingredient.class)
                .stream()
                .filter(e -> (listItemsXBurger.stream().map(String::toUpperCase).collect(Collectors.toList())).contains(e.getDescription().toUpperCase()))
                .collect(Collectors.toList());

        snack = new Snack("X-Burger", ingredientList);
        mongoTemplate.save(snack);
        ingredientList.clear();


        final List<String> listItemsXEgg = asList("Ovo", "Hambúrguer de carne", "Queijo");
        ingredientList = mongoTemplate.findAll(Ingredient.class)
                .stream()
                .filter(e -> (listItemsXEgg.stream().map(String::toUpperCase).collect(Collectors.toList())).contains(e.getDescription().toUpperCase()))
                .collect(Collectors.toList());
        snack = new Snack("X-Egg", ingredientList);
        mongoTemplate.save(snack);
        ingredientList.clear();


        final List<String> listItemsXEggBacon = asList("Ovo", "Bacon", "Hambúrguer de carne", "Queijo");
        ingredientList = mongoTemplate.findAll(Ingredient.class)
                .stream()
                .filter(e -> (listItemsXEggBacon.stream().map(String::toUpperCase).collect(Collectors.toList())).contains(e.getDescription().toUpperCase()))
                .collect(Collectors.toList());
        snack = new Snack("X-Egg Bacon", ingredientList);
        mongoTemplate.save(snack);
        ingredientList.clear();
    }

    @ChangeSet(order = "003", id = "addPromotions", author = "ddval")
    public void addPromotions(MongoTemplate mongoTemplate) {

        List<Ingredient> ingredientWith = asList(
                mongoTemplate.findOne(new Query(Criteria.where("description").is("Alface")), Ingredient.class));

        List<Ingredient> ingredientWithOut = asList(
                mongoTemplate.findOne(new Query(Criteria.where("description").is("Bacon")), Ingredient.class));

        Promotion promotion = new Promotion("Light", ingredientWith, ingredientWithOut, 1, 10.0, false);
        mongoTemplate.save(promotion);
        ingredientWithOut = new ArrayList<>();

        ingredientWith = asList(
                mongoTemplate.findOne(new Query(Criteria.where("description").is("Hambúrguer de carne")), Ingredient.class));

        promotion = new Promotion("Muita carne", ingredientWith, ingredientWithOut, 3, 33.3333, true);
        mongoTemplate.save(promotion);
        ingredientWithOut = new ArrayList<>();

        ingredientWith = asList(
                mongoTemplate.findOne(new Query(Criteria.where("description").is("Queijo")), Ingredient.class));

        promotion = new Promotion("Muito queijo", ingredientWith, ingredientWithOut, 3, 33.3333, true);
        mongoTemplate.save(promotion);
    }
}
