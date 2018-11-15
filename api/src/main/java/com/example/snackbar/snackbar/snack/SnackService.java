package com.example.snackbar.snackbar.snack;

import com.example.snackbar.snackbar.ingredient.Ingredient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SnackService {

    @Autowired
    private SnackRepository snackRepository;

    public Double calculateCost(Snack snack) {
        return snack.getIngredients().stream().mapToDouble(Ingredient::getCost).sum();
    }

    public Page<Snack> getAllSnacks(Pageable pageable) {
        return snackRepository.findAll(pageable);
    }

    public Boolean exists(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new IllegalArgumentException("Value can not be null or be blank.");
        }
        return snackRepository.exists(id);
    }

    public Snack findById(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new IllegalArgumentException("Value can not be null or be blank.");
        }
        return snackRepository.findById(id);
    }
}
