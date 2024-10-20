package com.example.grocery_store_sales_online.service;

import com.example.grocery_store_sales_online.model.product.ProductCategory;
import com.example.grocery_store_sales_online.projection.product.ProductCategoryProjection;
import com.example.grocery_store_sales_online.service.common.*;

import java.util.List;

public interface IProductCategoryService extends ISaveModelAble<ProductCategory> , IFindByHrefAble<ProductCategory,String>
        , IFindAllChildrenAble<ProductCategoryProjection>, IFindByIdAble<ProductCategory,Long> {

    List<ProductCategoryProjection> listProductCategoryChildren(Long id);
    List<ProductCategoryProjection> findAllMenu();
}
