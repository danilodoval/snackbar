package com.example.snackbar.snackbar.promotion;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepository extends CrudRepository<Promotion, String> {

    Promotion findById(String id);

    List<Promotion> findAll();

}
