package com.example.grocery_store_sales_online.model.product;
import com.example.grocery_store_sales_online.model.File.Image;
import com.example.grocery_store_sales_online.model.common.Model;
import com.example.grocery_store_sales_online.model.shop.Promotion;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Table(name = "product_item")
@Entity
@Getter
@Setter
public class ProductItem extends Model {
    private Double price;
    private Long qtyInStock;
    @Column(unique = true)
    private String sku;
    @ManyToMany()
    @JoinTable(name = "product_configuration", joinColumns = { @JoinColumn(name = "product_item_id") },
            inverseJoinColumns = {@JoinColumn(name = "variation_option_id") })
    private List<VariationOption> variationOptions = new ArrayList<>();
    @ManyToMany
    @JoinTable(name = "product_item_promotion", joinColumns = { @JoinColumn(name = "product_item_id") },
            inverseJoinColumns = {@JoinColumn(name = "promotion_id") })
    private  List<Promotion> promotions = new ArrayList<>();
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "product_item_image", joinColumns = { @JoinColumn(name = "product_item_id") },
            inverseJoinColumns = {@JoinColumn(name = "image_id") })
    private List<Image> images = new ArrayList<>();
    @ManyToOne
    private Product product;
}
