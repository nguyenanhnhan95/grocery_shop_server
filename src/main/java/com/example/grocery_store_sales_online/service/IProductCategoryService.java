package com.example.grocery_store_sales_online.service;

import com.example.grocery_store_sales_online.model.product.ProductCategory;
import com.example.grocery_store_sales_online.service.common.*;

import java.util.List;

public interface IProductCategoryService extends ISaveModelAble<ProductCategory> , IFindByHrefAble<ProductCategory,String>
        ,IFindAllByParent<ProductCategory>, IFindAllChildrenAble<ProductCategory>, IFindByIdAble<ProductCategory,Long> {

    List<ProductCategory> listProductCategoryChildren(ProductCategory productCategory);
    List<ProductCategory> findAllMenu();
}
