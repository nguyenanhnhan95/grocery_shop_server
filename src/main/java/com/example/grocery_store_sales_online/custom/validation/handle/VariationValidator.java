package com.example.grocery_store_sales_online.custom.validation.handle;


import com.example.grocery_store_sales_online.custom.validation.VariationConstraint;
import com.example.grocery_store_sales_online.repository.variation.VariationRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
@Slf4j
@RequiredArgsConstructor
public class VariationValidator implements ConstraintValidator<VariationConstraint,Object> {
    private final VariationRepository variationRepository;

    @Override
    @SuppressWarnings("unchecked")
    public boolean isValid(Object ids, ConstraintValidatorContext context) {
        if (ids == null) {
            return true;
        }
        try {
            if (ids instanceof Long) {
                return variationRepository.findById((Long) ids).isPresent();
            } else if (ids instanceof List) {
                List<Long> idList = (List<Long>) ids;

                if (idList.isEmpty()) {
                    return true;
                }
                for (Long id : idList) {
                    if (id!=null && variationRepository.findById( id).isEmpty()) {
                        return false;
                    }
                }
                return true;

            }
        }catch (Exception ex){
            log.error("Exception occurred while persisting VariationValidator:isValid to validate , Exception message {}", ex.getMessage());
            return false;
        }
        return true;
    }
}
