package com.example.grocery_store_sales_online.projection.product;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static com.example.grocery_store_sales_online.utils.CommonUtils.convertStringToList;
import static com.example.grocery_store_sales_online.utils.CommonUtils.convertStringToListLong;

@Getter
@Setter
public class ProductItemProjection {
    private Long id;
    private List<String> images;
    private Double price;
    private Long qtyInStock;
    private String sku;
    private List<Long> promotions;
    private List<Long> variationOptions;
    @QueryProjection
    public ProductItemProjection(String images, Double price, Long qtyInStock, String sku, List<Long> promotions, List<Long> variationOptions) {
        this.images = convertStringToList(images);
        this.price = price;
        this.qtyInStock = qtyInStock;
        this.sku = sku;
        this.promotions = promotions;
        this.variationOptions = variationOptions;
    }
    @QueryProjection
    public ProductItemProjection(Long id, String images, Double price, Long qtyInStock, String sku, String promotions, String variationOptions) {
        this.id = id;
        this.images = convertStringToList(images);
        this.price = price;
        this.qtyInStock = qtyInStock;
        this.sku = sku;
        this.promotions = convertStringToListLong(promotions);
        this.variationOptions = convertStringToListLong(variationOptions);
    }

}
