package com.example.snackbar.snackbar.snack;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.snackbar.snackbar.ingredient.Ingredient;

@Service
public class SnackService {

    private SnackRepository snackRepository;

    public SnackService(SnackRepository snackRepository) {
        this.snackRepository = snackRepository;
    }

    public Double calculateCost(Snack snack) {
        return snack.getIngredients().stream().mapToDouble(Ingredient::getCost).sum();
    }

    public Page<Snack> getAllSnacks(Pageable pageable) {
        return snackRepository.findAll(pageable);
    }

    public Boolean exists(String id) {
        return snackRepository.exists(id);
    }

    public Snack findById(String id) {
        return snackRepository.findById(id);
    }
}
