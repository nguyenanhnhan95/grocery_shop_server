package com.example.grocery_store_sales_online.projection.product;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductFormProjection {
    private List<String> images;
    private String name;
    private String brand;
    private Long productCategory;
    private Long variation;
    private String description;
    private List<ProductItemProjection> productItems;
    @QueryProjection
    public ProductFormProjection(List<String> images, String name, String brand, Long productCategory, Long variation, String description, List<ProductItemProjection> productItems) {
        this.images = images;
        this.name = name;
        this.brand = brand;
        this.productCategory = productCategory;
        this.variation = variation;
        this.description = description;
        this.productItems = productItems;
    }
}
