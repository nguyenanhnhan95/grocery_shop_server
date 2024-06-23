package com.example.grocery_store_sales_online.model.product;

import com.example.grocery_store_sales_online.model.common.Model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product_category", uniqueConstraints = @UniqueConstraint(columnNames ={"href"}),
        indexes = {@Index(columnList = "href")})
@Getter
@Setter
@RequiredArgsConstructor
public class ProductCategory extends Model {
    private String name;
    private String href;
    private String description;
    @ManyToOne
    private ProductCategory parentCategory;
    @Transient
    private List<ProductCategory> children=new ArrayList<>();
}
