package com.example.grocery_store_sales_online.custom.mapper.product;


import com.example.grocery_store_sales_online.dto.product.ProductItemDto;
import com.example.grocery_store_sales_online.dto.product.ProductItemEditDto;
import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.exception.MapperException;
import com.example.grocery_store_sales_online.model.product.ProductItem;
import com.example.grocery_store_sales_online.model.product.VariationOption;
import com.example.grocery_store_sales_online.model.shop.Promotion;
import com.example.grocery_store_sales_online.repository.Promotion.impl.PromotionRepository;
import com.example.grocery_store_sales_online.repository.variationOption.VariationOptionRepository;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
@Slf4j
public abstract class ProductItemMapper {
    @Autowired
    private PromotionRepository promotionRepository;
    @Autowired
    private VariationOptionRepository variationOptionRepository;
    @Mapping(target = "images", ignore = true)
    @Mapping(source = "price", target = "price")
    @Mapping(source = "qtyInStock", target = "qtyInStock")
    @Mapping(source = "sku", target = "sku")
    @Mapping(source = "promotions", target = "promotions",qualifiedByName = "mapPromotion")
    @Mapping(source = "variationOptions", target = "variationOptions",qualifiedByName = "mapVariationOption")
    public abstract ProductItem convertProductItemDtoToProductItem(ProductItemDto productItemDto);
    @Mapping(target = "images", ignore = true)
    @Mapping(source = "price", target = "price")
    @Mapping(source = "qtyInStock", target = "qtyInStock")
    @Mapping(source = "sku", target = "sku")
    @Mapping(source = "promotions", target = "promotions",qualifiedByName = "mapPromotion")
    @Mapping(source = "variationOptions", target = "variationOptions",qualifiedByName = "mapVariationOption")
    public abstract ProductItem updateProductItemDtoToProductItem(ProductItemEditDto productItemEditDto, @MappingTarget ProductItem productItem);
    @Named("mapPromotion")
    protected List<Promotion> mapPromotion(List<Long> ids) {
        try {
            if (ids == null || ids.isEmpty()) {
                throw new MapperException(EResponseStatus.NOT_FOUND_DATA.getLabel());
            }else {
                return ids.stream()
                        .map(id -> promotionRepository.findById(id)
                                .orElseThrow(() -> new MapperException(EResponseStatus.NOT_FOUND_DATA.getLabel())))
                        .collect(Collectors.toList());
            }

        }catch (MapperException ex){
            throw ex;
        }catch (Exception ex){
            throw new MapperException(ex.getMessage());
        }
    }
    @Named("mapVariationOption")
    protected List<VariationOption> mapVariationOption(List<Long> ids) {
        try {
            if (ids == null || ids.isEmpty()) {
                throw new MapperException(EResponseStatus.NOT_FOUND_DATA.getLabel());
            }else {
                return ids.stream()
                        .map(id -> variationOptionRepository.findById(id)
                                .orElseThrow(() -> new MapperException(EResponseStatus.NOT_FOUND_DATA.getLabel())))
                        .collect(Collectors.toList());
            }

        }catch (MapperException ex){
            throw ex;
        }catch (Exception ex){
            throw new MapperException(ex.getMessage());
        }
    }
}
