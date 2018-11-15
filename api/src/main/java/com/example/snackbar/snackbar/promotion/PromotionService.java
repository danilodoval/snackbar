package com.example.snackbar.snackbar.promotion;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    public List<Promotion> getAllPromotion() {
        return promotionRepository.findAll();
    }

    public Boolean exists(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new IllegalArgumentException("Value can not be null or be blank.");
        }
        return promotionRepository.exists(id);
    }

    public Promotion findById(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new IllegalArgumentException("Value can not be null or be blank.");
        }
        return promotionRepository.findById(id);
    }
}
