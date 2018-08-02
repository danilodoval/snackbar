package com.example.snackbar.snackbar.promotion;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PromotionService {

    private PromotionRepository promotionRepository;

    public PromotionService(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    public List<Promotion> getAllPromotion() {
        return promotionRepository.findAll();
    }

    public Boolean exists(String id) {
        return promotionRepository.exists(id);
    }

    public Promotion findById(String id) {
        return promotionRepository.findById(id);
    }
}
