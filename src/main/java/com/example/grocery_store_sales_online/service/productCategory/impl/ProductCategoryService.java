package com.example.grocery_store_sales_online.service.productCategory.impl;

import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.exception.ServiceBusinessExceptional;
import com.example.grocery_store_sales_online.model.product.ProductCategory;
import com.example.grocery_store_sales_online.repository.productCategory.ProductCategoryRepository;
import com.example.grocery_store_sales_online.service.base.impl.BaseService;
import com.example.grocery_store_sales_online.service.productCategory.IProductCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductCategoryService extends BaseService implements IProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;
    Logger logger = LoggerFactory.getLogger(ProductCategoryService.class);
    @Override
    public ProductCategory saveProductCategory(ProductCategory productCategory) {
        try {
            log.info("ProductCategoryService:saveProductCategory execution started.");
            setMetaData(productCategory);
            setPersonAction(productCategory);
            return Optional.of(productCategoryRepository.save(productCategory)).orElse(null);
        }catch (Exception ex){
            log.error("Exception occurred while persisting ProductCategoryService:saveProductCategory save to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.SAVE_FAIL.getLabel(), EResponseStatus.SAVE_FAIL);
        }
    }

    @Override
    public ProductCategory findByHref(String href) {
        try {
            log.info("ProductCategoryService:findByHref execution started.");
            return productCategoryRepository.findByHref(href).orElse(null);
        }catch (Exception ex){
            log.error("Exception occurred while persisting ProductCategoryService:findByHref to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL);
        }
    }

    @Override
    public List<ProductCategory> listProductCategoryChildren(ProductCategory productCategory) {
        try {
            log.info("ProductCategoryService:listProductCategoryChildren execution started.");
            return productCategoryRepository.listProductCategoryChildren(productCategory);
        }catch (Exception ex){
            log.error("Exception occurred while persisting ProductCategoryService:listProductCategoryChildren to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL);
        }
    }

    @Override
    public List<ProductCategory> findAllProductCategories() {
        try {
            log.info("ProductCategoryService:findAllProductCategories execution started.");
            List<ProductCategory> productCategories =productCategoryRepository.findAllParent();
            productCategories.forEach(each->{
                this.listProductCategoryChildren(each).forEach(children->{
                    children.setParentCategory(null);
                });
                each.setChildren(this.listProductCategoryChildren(each));
            });
            return productCategories;
        }catch (Exception ex){
            log.error("Exception occurred while persisting ProductCategoryService:findAllProductCategories to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL);
        }
    }

}
