package com.example.grocery_store_sales_online.projection;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class ProductProjection {

    private Long id;
    private String name;
    private String brand;
    private String description;
    private String categoryName;
    private List<ImageProjection> images;

    public ProductProjection() {}

    @QueryProjection
    public ProductProjection(Long id, String name,String brand, String description,String categoryName, List<ImageProjection> images) {
        this.id = id;
        this.name = name;
        this.brand=brand;
        this.description = description;
        this.categoryName=categoryName;
        this.images = images;
    }
}
