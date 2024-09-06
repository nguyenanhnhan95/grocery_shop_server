package com.example.grocery_store_sales_online.custom.mapper.product;


import com.example.grocery_store_sales_online.dto.product.ProductItemDto;

import com.example.grocery_store_sales_online.model.product.ProductItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductItemMapper {
    @Mapping(source = "price", target = "price")
    @Mapping(source = "qtyInStock", target = "qtyInStock")
    @Mapping(source = "sky", target = "sky")
    ProductItem convertProductItemDtoToProductItem(ProductItemDto productItemDto);
}
