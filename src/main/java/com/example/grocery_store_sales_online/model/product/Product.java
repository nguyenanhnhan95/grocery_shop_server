package com.example.grocery_store_sales_online.model.product;

import com.example.grocery_store_sales_online.model.File.Image;
import com.example.grocery_store_sales_online.model.File.QImage;
import com.example.grocery_store_sales_online.model.common.Model;
import com.example.grocery_store_sales_online.model.shop.Promotion;
import com.querydsl.core.annotations.QueryProjection;
import com.querydsl.core.types.dsl.ListPath;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@Getter
@Setter
public class Product extends Model {
    private String name;
    private String brand;
    @Column(columnDefinition = "longtext")
    private String description;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "product_image", joinColumns = { @JoinColumn(name = "product_id") },
            inverseJoinColumns = {@JoinColumn(name = "image_id") })
    private List<Image> images = new ArrayList<>();
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductItem> productItems = new ArrayList<>();
    @ManyToOne
    private ProductCategory productCategory;
    @ManyToOne
    private Variation variation;
    public Product(){

    }
//    @QueryProjection
//    public Product(Long id, String name, String description,@Nullable ListPath<Image, QImage> images) {
//        super(id);
//        this.name = name;
//        this.description = description;
//    }
}
