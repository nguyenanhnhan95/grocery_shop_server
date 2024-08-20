package com.example.grocery_store_sales_online.mapper.product;

import com.example.grocery_store_sales_online.dto.product.VariationOptionDto;
import com.example.grocery_store_sales_online.model.product.VariationOption;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VariationOptionMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    VariationOption convertDtoToVariationOption(VariationOptionDto variationOptionDto);
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    void updateDtoToVariationOption(VariationOptionDto variationOptionDto, @MappingTarget VariationOption variationOption);
}
