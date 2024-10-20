package com.example.grocery_store_sales_online.custom.validation.handle;


import com.example.grocery_store_sales_online.custom.validation.PromotionConstraint;
import com.example.grocery_store_sales_online.repository.Promotion.impl.PromotionRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class PromotionValidator implements ConstraintValidator<PromotionConstraint, Object> {
    private final PromotionRepository promotionRepository;

    @Override
    @SuppressWarnings("unchecked")
    public boolean isValid(Object ids, ConstraintValidatorContext context) {
        if (ids == null) {
            return true;
        }
        try {
            if (ids instanceof Long) {

                return promotionRepository.findById((Long) ids).isPresent();
            } else if (ids instanceof List) {
                List<Long> idList = (List<Long>) ids;

                if (idList.isEmpty()) {
                    return true;
                }
                for (Long id : idList) {
                    if (id!=null && promotionRepository.findById( id).isEmpty()) {
                        return false;
                    }
                }
                return true;

            }
        } catch (Exception ex) {
            log.error("Exception occurred while persisting PromotionValidator:isValid to validate , Exception message {}", ex.getMessage());
            return false;
        }
        return true;
    }
}
