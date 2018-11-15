package com.example.snackbar.snackbar.ingredient;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;

    public Page<Ingredient> getAllIngredients(Pageable pageable) {
        return ingredientRepository.findAll(pageable);
    }

    public Boolean exists(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new IllegalArgumentException("Value can not be null or be blank.");
        }
        return ingredientRepository.exists(id);
    }

    public Ingredient findById(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new IllegalArgumentException("Value can not be null or be blank.");
        }
        return ingredientRepository.findById(id);
    }
}
