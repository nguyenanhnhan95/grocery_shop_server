package com.example.grocery_store_sales_online.mapper;

import com.example.grocery_store_sales_online.dto.product.VariationOptionDto;
import com.example.grocery_store_sales_online.model.product.VariationOption;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VariationOptionMapper {
    VariationOption convertDtoToVariationOption(VariationOptionDto variationOptionDto);
    void updateDtoToVariationOption(VariationOptionDto variationOptionDto, @MappingTarget VariationOption variationOption);
}
