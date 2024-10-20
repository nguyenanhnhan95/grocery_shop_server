package com.example.grocery_store_sales_online.projection.product;


import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProductCategoryProjection {
    private Long id;
    private String name;
    private String href;
    private String description;

    public ProductCategoryProjection() {
    }
    @QueryProjection
    public ProductCategoryProjection(Long id, String name, String href, String description) {
        this.id = id;
        this.name = name;
        this.href = href;
        this.description = description;
    }

    private ProductCategoryProjection parentCategory;

    private List<ProductCategoryProjection> children=new ArrayList<>();
}
