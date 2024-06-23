package com.example.grocery_store_sales_online.service.productCategory;

import com.example.grocery_store_sales_online.model.product.ProductCategory;

import java.util.List;

public interface IProductCategoryService{
    ProductCategory saveProductCategory(ProductCategory productCategory);
    ProductCategory findByHref(String href);
    List<ProductCategory> listProductCategoryChildren(ProductCategory productCategory);
    List<ProductCategory> findAllProductCategories();
}
