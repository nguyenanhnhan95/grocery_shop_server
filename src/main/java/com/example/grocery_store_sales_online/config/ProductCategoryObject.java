package com.example.grocery_store_sales_online.config;

import com.example.grocery_store_sales_online.model.product.ProductCategory;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class ProductCategoryObject {
    private List<ProductCategory> productCategories=new ArrayList<>();
}
