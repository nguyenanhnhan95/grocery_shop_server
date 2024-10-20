package com.example.grocery_store_sales_online.service.impl;

import com.example.grocery_store_sales_online.dto.product.ProductEditDto;
import com.example.grocery_store_sales_online.dto.product.ProductItemDto;
import com.example.grocery_store_sales_online.dto.product.ProductItemEditDto;
import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.exception.EmptyException;
import com.example.grocery_store_sales_online.exception.ServiceBusinessExceptional;
import com.example.grocery_store_sales_online.custom.mapper.product.ProductItemMapper;
import com.example.grocery_store_sales_online.model.File.Image;
import com.example.grocery_store_sales_online.model.product.Product;
import com.example.grocery_store_sales_online.model.product.ProductItem;
import com.example.grocery_store_sales_online.projection.product.ProductItemProjection;
import com.example.grocery_store_sales_online.projection.product.ProductManageProjection;
import com.example.grocery_store_sales_online.repository.image.impl.ImageRepository;
import com.example.grocery_store_sales_online.repository.productItem.impl.ProductItemRepository;
import com.example.grocery_store_sales_online.service.IImageService;
import com.example.grocery_store_sales_online.service.IProductItemService;
import com.example.grocery_store_sales_online.utils.QueryListResult;
import com.example.grocery_store_sales_online.utils.QueryParameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductItemServiceImpl extends BaseServiceImpl implements IProductItemService {
    private final ProductItemRepository productItemRepository;
    private final ProductItemMapper productItemMapper;
    private final IImageService imageService;

    @Override
    public ProductItem saveModelDto(ProductItemDto modelDto, Product product) {
        log.info("ProductItemServiceImpl:saveModelDto execution started.");
        try {
            ProductItem productItem = productItemMapper.convertProductItemDtoToProductItem(modelDto);
            List<Image> imageItem = new ArrayList<>(imageService.handleToImages(modelDto.getImages(), "productitem/"));
            productItem.setImages(imageItem);
            productItem.setProduct(product);
            setMetaData(productItem);
            setPersonAction(productItem);
            return  productItemRepository.saveModel(productItem);
        }catch (Exception ex){
            log.error("Exception occurred while persisting RoleService:saveModelDto  to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.SAVE_FAIL.getLabel(), EResponseStatus.SAVE_FAIL.getCode());
        }
    }

    @Override
    public ProductItem updateModelDto(ProductItemEditDto model, Product product) {
        log.info("ProductItemServiceImpl:findByIdProductAble execution started.");
        try {
            ProductItem productItemOlder = this.findById(model.getId());
            ProductItem productItem = productItemMapper.updateProductItemDtoToProductItem(model,productItemOlder);
            List<Image> imageItem = new ArrayList<>(imageService.handleToImages(model.getImages(), "productitem/"));
            productItem.setImages(imageItem);
            productItem.setProduct(product);
            setMetaData(productItem);
            setPersonAction(productItem);
            return  productItemRepository.saveModel(productItem);
        }catch (Exception ex){
            log.error("Exception occurred while persisting ProductItemServiceImpl:findById  to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.SAVE_FAIL.getLabel(), EResponseStatus.SAVE_FAIL.getCode());
        }
    }

    @Override
    public List<ProductItemProjection> findByIdProductAble(Long id) {
        log.info("ProductItemServiceImpl:findByIdProductAble execution started.");
        try {
            return productItemRepository.findByIdProduct(id);
        } catch (Exception ex) {
            log.error("Exception occurred while persisting RoleService:findById  to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }
    @Override
    public ProductItem findById(Long id) {
        try {
            log.info("ProductItemServiceImpl:findById execution started.");
            return productItemRepository.findById(id)
                    .orElseThrow(() ->
                            new EmptyException(
                                    EResponseStatus.DATA_EMPTY.getLabel()
                            )
                    );
        } catch (EmptyException ex) {
            throw ex;
        }catch (Exception ex){
            log.error("Exception occurred while persisting ProductItemServiceImpl:findById to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }
    @Override
    public ProductItem saveModel(ProductItem model) {
        try {
            log.info("FileEntryService:saveModel(file,directory) execution started.");
            List<Image> images = new ArrayList<>();
            for (Image image: model.getImages()) {
                images.add(imageService.saveModel(image));
            }
            model.setImages(images);
            setMetaData(model);
            setPersonAction(model);
            return productItemRepository.saveModel(model);
        }catch (Exception ex){
            log.error("Exception occurred while persisting ProductItemService:saveModel database and server , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.SAVE_FAIL.getLabel(), EResponseStatus.SAVE_FAIL.getCode());
        }
    }

    @Override
    public QueryListResult<ProductManageProjection> getListResultManage(String queryParameter) {
        try {
            log.info("ProductItemServiceImpl:getListResultManage execution started.");
            return productItemRepository.getListResultManage(readJsonQuery(queryParameter, QueryParameter.class));
        }catch (Exception ex){
            log.error("Exception occurred while persisting ProductItemServiceImpl:getListResultManage to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }


}
