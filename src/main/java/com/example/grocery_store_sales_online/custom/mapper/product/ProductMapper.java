package com.example.grocery_store_sales_online.custom.mapper.product;
import com.example.grocery_store_sales_online.dto.product.ProductDto;
import com.example.grocery_store_sales_online.dto.product.ProductEditDto;
import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.exception.MapperException;
import com.example.grocery_store_sales_online.model.person.Role;
import com.example.grocery_store_sales_online.model.product.Product;
import com.example.grocery_store_sales_online.model.product.ProductCategory;
import com.example.grocery_store_sales_online.model.product.Variation;
import com.example.grocery_store_sales_online.repository.productCategory.impl.ProductCategoryRepository;
import com.example.grocery_store_sales_online.repository.variation.VariationRepository;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
@Slf4j
public abstract class ProductMapper {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private VariationRepository variationRepository;
    @Mapping(target = "images", ignore = true)
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "brand", target = "brand")
    @Mapping(source = "productCategory", target = "productCategory",qualifiedByName = "mapProductCategory")
    @Mapping(source = "variation", target = "variation",qualifiedByName = "mapVariation")
    @Mapping(target = "productItems", ignore = true)
    public abstract Product  convertProductDtoToProduct(ProductDto productDto);
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "brand", target = "brand")
    @Mapping(source = "productCategory", target = "productCategory",qualifiedByName = "mapProductCategory")
    @Mapping(source = "variation", target = "variation",qualifiedByName = "mapVariation")
    @Mapping(target = "productItems", ignore = true)
    public abstract Product  updateProductDtoToProduct(ProductEditDto productEditDto , @MappingTarget Product product);
    @Named("mapVariation")
    protected Variation mapVariation(Long roleId) {
        try {
            return variationRepository.findById(roleId)
                    .orElseThrow(() -> {
                        throw new MapperException(EResponseStatus.NOT_FOUND_DATA.getLabel());
                    });
        }catch (MapperException ex){
            throw ex;
        }catch (Exception ex){
            throw new MapperException(ex.getMessage());
        }
    }
    @Named("mapProductCategory")
    protected ProductCategory mapProductCategory(Long roleId) {
        try {
            return productCategoryRepository.findById(roleId)
                    .orElseThrow(() -> {
                        throw new MapperException(EResponseStatus.NOT_FOUND_DATA.getLabel());
                    });
        }catch (MapperException ex){
            throw ex;
        }catch (Exception ex){
            throw new MapperException(ex.getMessage());
        }

    }
}
