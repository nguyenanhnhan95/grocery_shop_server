package com.example.grocery_store_sales_online.config;

import com.example.grocery_store_sales_online.model.product.ProductCategory;
import com.example.grocery_store_sales_online.utils.ResourceJsonLoader;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class CategoryProductProperties {
    Logger logger = LoggerFactory.getLogger(CategoryProductProperties.class);

    private static final String CONFIG_FILE = "productCategories.json";
    @Getter
    private List<ProductCategory> productCategories = new ArrayList<ProductCategory>();

    public CategoryProductProperties() {
        try {
            logger.info("CategoryProductProperties execution started.");
            ProductCategoryObject productCategoryObject = new ResourceJsonLoader().readValue(CONFIG_FILE, ProductCategoryObject.class);
            if (productCategoryObject != null) {
                productCategories = productCategoryObject.getProductCategories();
            }
            logger.info("Initial home menu");
        } catch (Exception e) {
            logger.error("Loading main menu home with error: {}", e.getMessage());
        }
    }
}
