package com.example.snackbar.snackbar.ingredient;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface IngredientRepository extends CrudRepository<Ingredient, String> {

    Ingredient findById(String id);

    Page<Ingredient> findAll(Pageable pageable);

}
