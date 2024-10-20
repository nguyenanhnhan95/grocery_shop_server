package com.example.grocery_store_sales_online.projection.product;

import com.example.grocery_store_sales_online.projection.file.ImageProjection;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
public class ProductManageProjection {
    private Long id;
    private String name;
    private List<ImageProjection>  images;
    private String brand;
    private String productCategory;
    private String optionVariation;
    private Double price;
    private Long qtyInStock;
    private String sky;
    private String promotion;
    @QueryProjection
    public ProductManageProjection(Long id, String name, List<ImageProjection> images, String brand, String productCategory, String optionVariation) {
        this.id = id;
        this.name = name;
        this.images = images;
        this.brand = brand;
        this.productCategory = productCategory;
        this.optionVariation = optionVariation;
    }
    @QueryProjection
    public ProductManageProjection(Long id, String name, List<ImageProjection> images, String brand, String productCategory, String optionVariation, Double price, Long qtyInStock, String sky, String promotion) {
        this.id = id;
        this.name = name;
        this.images = images;
        this.brand = brand;
        this.productCategory = productCategory;
        this.optionVariation = optionVariation;
        this.price = price;
        this.qtyInStock = qtyInStock;
        this.sky = sky;
        this.promotion = promotion;
    }
}
