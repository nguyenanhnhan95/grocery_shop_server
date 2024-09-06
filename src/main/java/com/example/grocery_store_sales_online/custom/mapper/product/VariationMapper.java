package com.example.grocery_store_sales_online.custom.mapper.product;

import com.example.grocery_store_sales_online.dto.product.VariationDto;
import com.example.grocery_store_sales_online.model.product.Variation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VariationMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    Variation convertVariationDtoToVariation(VariationDto variationDto);
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    void updateVariationFromDto(VariationDto variationDto, @MappingTarget Variation variation);
}
