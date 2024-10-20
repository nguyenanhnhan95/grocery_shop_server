package com.example.grocery_store_sales_online.service;

import com.example.grocery_store_sales_online.dto.product.ProductDto;
import com.example.grocery_store_sales_online.dto.product.ProductEditDto;
import com.example.grocery_store_sales_online.model.product.Product;
import com.example.grocery_store_sales_online.projection.product.ProductProjection;
import com.example.grocery_store_sales_online.service.common.IFindByIdAble;
import com.example.grocery_store_sales_online.service.common.IGetResultListAble;
import com.example.grocery_store_sales_online.service.common.ISaveModelAble;
import com.example.grocery_store_sales_online.service.common.ISaveModelDtoAble;
import jakarta.servlet.http.HttpServletRequest;

public interface IProductService extends IGetResultListAble<ProductProjection>, ISaveModelAble<Product>, IFindByIdAble<Product,Long> {
    void saveProductDto(ProductDto productDto, HttpServletRequest httpServletRequest) ;
    void editProductDto(Long id, ProductEditDto productEditDto, HttpServletRequest httpServletRequest) ;
    ProductProjection findByIdProductEdit(Long id);

}
