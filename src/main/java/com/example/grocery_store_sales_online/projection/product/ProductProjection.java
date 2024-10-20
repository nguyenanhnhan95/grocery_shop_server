package com.example.grocery_store_sales_online.projection.product;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static com.example.grocery_store_sales_online.utils.CommonUtils.convertStringToList;
import static com.example.grocery_store_sales_online.utils.CommonUtils.convertStringToListLong;

@Getter
@Setter
public class ProductProjection {

    private Long id;
    private String name;
    private String brand;
    private String description;
    private String nameProductCategory;
    private Long productCategory;
    private Long qtyInStock;
    private Long variation;
    private List<String> images;
    private List<ProductItemProjection> productItems;


    public ProductProjection() {}

    @QueryProjection
    public ProductProjection(Long id, String name, String brand, String nameProductCategory, Long qtyInStock, String images) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.nameProductCategory = nameProductCategory;
        this.qtyInStock = qtyInStock;
        this.images = convertStringToList(images);
    }
    @QueryProjection
    public ProductProjection(Long id, String name, String brand, String description, Long variation, Long productCategory , String images) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.variation = variation;
        this.productCategory = productCategory;
        this.images = convertStringToList(images);
    }
}
