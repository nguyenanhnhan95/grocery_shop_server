package com.example.grocery_store_sales_online.service.impl;

import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.exception.ServiceBusinessExceptional;
import com.example.grocery_store_sales_online.model.product.ProductCategory;
import com.example.grocery_store_sales_online.projection.product.ProductCategoryProjection;
import com.example.grocery_store_sales_online.repository.productCategory.impl.ProductCategoryRepository;
import com.example.grocery_store_sales_online.service.IProductCategoryService;
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
public class ProductCategoryServiceImpl extends BaseServiceImpl implements IProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;
    Logger logger = LoggerFactory.getLogger(ProductCategoryServiceImpl.class);

    @Override
    public ProductCategory saveModel(ProductCategory productCategory) {
        try {
            log.info("ProductCategoryService:saveProductCategory execution started.");
            setMetaData(productCategory);
            setPersonAction(productCategory);
            return Optional.of(productCategoryRepository.save(productCategory)).orElse(null);
        } catch (Exception ex) {
            log.error("Exception occurred while persisting ProductCategoryService:saveProductCategory save to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.SAVE_FAIL.getLabel(), EResponseStatus.SAVE_FAIL.getCode());
        }
    }

    @Override
    public ProductCategory findByHref(String href) {
        try {
            log.info("ProductCategoryService:findByHref execution started.");
            return productCategoryRepository.findByHref(href).orElse(null);
        } catch (Exception ex) {
            log.error("Exception occurred while persisting ProductCategoryService:findByHref to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    public List<ProductCategoryProjection> listProductCategoryChildren(Long id) {
        try {
            log.info("ProductCategoryService:listProductCategoryChildren execution started.");
            return productCategoryRepository.findAllByParent(id);
        } catch (Exception ex) {
            log.error("Exception occurred while persisting ProductCategoryService:listProductCategoryChildren to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    public List<ProductCategoryProjection> findAllMenu() {
        try {
            log.info("ProductCategoryService:findAllProductCategories execution started.");
            List<ProductCategoryProjection> productCategories = productCategoryRepository.findAllParent();
            productCategories.forEach(each -> {
                this.listProductCategoryChildren(each.getId()).forEach(children -> {
                    children.setParentCategory(null);
                });
                each.setChildren(this.listProductCategoryChildren(each.getId()));
            });
            return productCategories;
        } catch (Exception ex) {
            log.error("Exception occurred while persisting ProductCategoryService:findAllProductCategories to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }


    @Override
    public List<ProductCategoryProjection> findAllChildren() {
        try {
            log.info("ProductCategoryService:findAllChildren execution started.");
            return productCategoryRepository.findAllChildren();
        } catch (Exception ex) {
            log.error("Exception occurred while persisting ProductCategoryService:findAllChildren to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    public ProductCategory findById(Long id) {
        log.info("ProductCategoryService:findById {} execution started.",id);
        try {
            return productCategoryRepository.findById(id)
                    .orElseThrow(() ->
                            new ServiceBusinessExceptional(
                                    EResponseStatus.NOT_FOUND_BY_ID.getLabel(),
                                    EResponseStatus.NOT_FOUND_BY_ID.getCode()
                            )
                    );
        } catch (ServiceBusinessExceptional ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Exception occurred while persisting ProductCategoryService:findById to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }
}
