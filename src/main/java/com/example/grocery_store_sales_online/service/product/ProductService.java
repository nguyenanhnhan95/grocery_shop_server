package com.example.grocery_store_sales_online.service.product;

import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.exception.ServiceBusinessExceptional;
import com.example.grocery_store_sales_online.model.product.Product;
import com.example.grocery_store_sales_online.repository.product.ProductRepository;
import com.example.grocery_store_sales_online.service.base.BaseService;
import com.example.grocery_store_sales_online.service.base.IBaseService;
import com.example.grocery_store_sales_online.utils.QueryListResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService extends BaseService implements IBaseService<Product> {

    private final ProductRepository productRepository;
    @Override
    public QueryListResult<Product> finAll(String queryParameter) {
        try {
            log.info("ProductService:finAll execution started.");
            return productRepository.findAll(readJsonQuery(queryParameter));
        }catch (Exception ex){
            log.error("Exception occurred while persisting ProductService:finAll to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL);
        }
    }
}
