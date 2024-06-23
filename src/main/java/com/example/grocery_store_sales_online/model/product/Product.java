package com.example.grocery_store_sales_online.model.product;

import com.example.grocery_store_sales_online.model.Image;
import com.example.grocery_store_sales_online.model.common.Model;
import com.example.grocery_store_sales_online.model.shop.Promotion;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@Getter
@Setter
public class Product extends Model {
    private String name;
    @Column(columnDefinition = "longtext")
    private String description;
    @Column(unique = true)
    private String sky;
    private Long qtyInStock;
    private Long price;
    @ManyToMany
    @JoinTable(name = "product_promotion", joinColumns = { @JoinColumn(name = "product_id") },
            inverseJoinColumns = {@JoinColumn(name = "promotion_id") })
    private  List<Promotion> promotions = new ArrayList<>();
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "product_image", joinColumns = { @JoinColumn(name = "product_id") },
            inverseJoinColumns = {@JoinColumn(name = "image_id") })
    private List<Image> images = new ArrayList<>();
    @ManyToOne
    private ProductCategory productCategory;
    @ManyToOne
    private Variation variation;
}
