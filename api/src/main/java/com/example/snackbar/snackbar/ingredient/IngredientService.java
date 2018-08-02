package com.example.snackbar.snackbar.ingredient;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class IngredientService {

    private IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public Page<Ingredient> getAllIngredients(Pageable pageable) {
        return ingredientRepository.findAll(pageable);
    }

    public Boolean exists(String id) {
        return ingredientRepository.exists(id);
    }

    public Ingredient findById(String id) {
        return ingredientRepository.findById(id);
    }
}
