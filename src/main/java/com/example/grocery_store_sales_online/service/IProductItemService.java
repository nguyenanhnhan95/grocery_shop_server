package com.example.grocery_store_sales_online.service;


import com.example.grocery_store_sales_online.dto.product.ProductItemDto;
import com.example.grocery_store_sales_online.dto.product.ProductItemEditDto;
import com.example.grocery_store_sales_online.model.product.Product;
import com.example.grocery_store_sales_online.model.product.ProductItem;
import com.example.grocery_store_sales_online.projection.product.ProductItemProjection;
import com.example.grocery_store_sales_online.projection.product.ProductManageProjection;
import com.example.grocery_store_sales_online.service.common.IFindByIdAble;
import com.example.grocery_store_sales_online.service.common.ISaveModelAble;

import com.example.grocery_store_sales_online.utils.QueryListResult;

import java.util.List;

public interface IProductItemService extends  ISaveModelAble<ProductItem>, IFindByIdAble<ProductItem,Long> {
    QueryListResult<ProductManageProjection> getListResultManage(String queryParameter);
    ProductItem saveModelDto(ProductItemDto model, Product product);
    ProductItem updateModelDto(ProductItemEditDto model, Product product);
    List<ProductItemProjection> findByIdProductAble(Long id);
}
