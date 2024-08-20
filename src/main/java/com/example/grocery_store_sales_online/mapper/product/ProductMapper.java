package com.example.grocery_store_sales_online.mapper.product;
import com.example.grocery_store_sales_online.dto.product.ProductDto;
import com.example.grocery_store_sales_online.model.product.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "brand", target = "brand")
    Product convertProductDtoToProduct(ProductDto productDto);
}
