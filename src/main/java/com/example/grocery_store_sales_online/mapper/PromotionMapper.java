package com.example.grocery_store_sales_online.mapper;

import com.example.grocery_store_sales_online.dto.shop.PromotionDto;
import com.example.grocery_store_sales_online.model.shop.Promotion;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PromotionMapper {
    Promotion convertDtoToPromotion(PromotionDto promotionDto);
    void updateDtoToPromotion(PromotionDto promotionDto, @MappingTarget Promotion promotion);
}
