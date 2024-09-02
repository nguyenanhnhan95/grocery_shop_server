package com.example.grocery_store_sales_online.service.impl;

import com.example.grocery_store_sales_online.dto.product.ProductDto;
import com.example.grocery_store_sales_online.enums.EResponseStatus;

import com.example.grocery_store_sales_online.exception.CustomValidationException;
import com.example.grocery_store_sales_online.exception.ServiceBusinessExceptional;
import com.example.grocery_store_sales_online.mapper.product.ProductItemMapper;
import com.example.grocery_store_sales_online.mapper.product.ProductMapper;
import com.example.grocery_store_sales_online.model.File.Image;
import com.example.grocery_store_sales_online.model.product.*;
import com.example.grocery_store_sales_online.model.shop.Promotion;
import com.example.grocery_store_sales_online.projection.product.ProductProjection;
import com.example.grocery_store_sales_online.repository.product.ProductRepository;
import com.example.grocery_store_sales_online.service.IImageService;
import com.example.grocery_store_sales_online.service.IProductService;
import com.example.grocery_store_sales_online.service.IProductCategoryService;
import com.example.grocery_store_sales_online.service.IProductItemService;
import com.example.grocery_store_sales_online.service.IPromotionService;
import com.example.grocery_store_sales_online.service.IVariationService;
import com.example.grocery_store_sales_online.service.IVariationOptionService;
import com.example.grocery_store_sales_online.utils.CommonConstants;
import com.example.grocery_store_sales_online.utils.ProcessImage;
import com.example.grocery_store_sales_online.utils.QueryListResult;
import com.example.grocery_store_sales_online.utils.QueryParameter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.example.grocery_store_sales_online.utils.CommonConstants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl extends BaseServiceImpl implements IProductService{
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final IImageService imageService;
    private final IProductCategoryService productCategoryService;
    private final ProductItemMapper productItemMapper;
    private final IVariationService variationService;
    private final IPromotionService promotionService;
    private final IVariationOptionService variationOptionService;
    private final IProductItemService productItemService;
    @Override
    public QueryListResult<ProductProjection> getListResult(String queryParameter) {
        try {
            log.info("ProductService:getListResult execution started.");
            return productRepository.getListResult(readJsonQuery(queryParameter, QueryParameter.class));
        }catch (Exception ex){
            log.error("Exception occurred while persisting ProductService:getListResult to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    public void preProcessSave(ProductDto productDto, List<MultipartFile> imagesProduct, List<MultipartFile> imageProductItems)  {
        try {
            Product product = productMapper.convertProductDtoToProduct(productDto);
            if(imagesProduct==null){
                throw createValidationException("ProductDto","images",CommonConstants.THIS_UPLOAD_FILE_ITEM_CANNOT_EMPTY);
            }
            if(imageProductItems==null){
                throw createValidationException("ProductDto","productItem[0].images",CommonConstants.THIS_UPLOAD_FILE_ITEM_CANNOT_EMPTY);
            }
            ProductCategory productCategory = productCategoryService.findById(productDto.getIdProductCategory())
                    .orElseThrow(() -> createValidationException("ProductDto", "idProductCategory", CommonConstants.THIS_FILED_DATA_NOT_EXIST));
            product.setProductCategory(productCategory);
            Variation variation = variationService.findById(productDto.getIdVariation())
                    .orElseThrow(() -> createValidationException("ProductDto", "idVariation", THIS_FILED_DATA_NOT_EXIST));
            product.setVariation(variation);
            List<Image> images = new ArrayList<>();
            imagesProduct.forEach(file->{
                if(file==null || !ProcessImage.checkExtensionImage(file.getOriginalFilename())){
                    throw createValidationException("ProductDto","images",THIS_FIELD_NOT_CORRECT_FORMAT);
                }
                if(file.getSize()>MAX_FILE_SIZE){
                    throw createValidationException("ProductDto","images",CommonConstants.THIS_FILE_SIZE_TOO_LARGE);
                }
                Image image = imageService.handleToImage(file,folderUrl());
                images.add(image);
            });
            product.setImages(images);
            Product productSaved=this.saveModel(product);
            for (int i = 0; i < imageProductItems.size(); i++) {
                if(imageProductItems.get(i) == null || !ProcessImage.checkExtensionImage(imageProductItems.get(i).getOriginalFilename())){
                    throw createValidationException("ProductDto","productItem["+i+"].images",CommonConstants.THIS_FIELD_NOT_CORRECT_FORMAT);
                }
                if(imageProductItems.get(i).getSize()>MAX_FILE_SIZE){
                    throw createValidationException("ProductDto","productItem["+i+"].images",CommonConstants.THIS_FILE_SIZE_TOO_LARGE);
                }
            }
            productDto.setMultipart(imagesProduct);
            int size= productDto.getProductItem().size();
            for (int i = 0; i < size; i++) {
                if(i<imageProductItems.size()) {
                    productDto.getProductItem().get(i).setMultipart(imageProductItems.get(i));
                }else {
                    throw createValidationException("ProductDto","productItem["+i+"].images", CommonConstants.THIS_UPLOAD_FILE_ITEM_CANNOT_EMPTY);
                }
                ProductItem  productItem = productItemMapper.convertProductItemDtoToProductItem(productDto.getProductItem().get(i));
                List<Promotion> promotions = new ArrayList<>();
                for (Long id : productDto.getProductItem().get(i).getIdPromotions()) {
                    int finalI = i;
                    Promotion promotion = promotionService.findById(id)
                            .orElseThrow(() -> createValidationException("ProductDto", "productItem[" + finalI + "].idPromotions", CommonConstants.THIS_FILED_DATA_NOT_EXIST));
                    promotions.add(promotion);
                }
                productItem.setPromotions(promotions);
                List<VariationOption> variationOptions = new ArrayList<>();
                for (Long id : productDto.getProductItem().get(i).getIdVariationOptions()) {
                    int finalI = i;
                    VariationOption variationOption = variationOptionService.findById(id)
                            .orElseThrow(() -> createValidationException("ProductDto", "productItem[" + finalI + "].idVariationOptions", CommonConstants.THIS_FILED_DATA_NOT_EXIST));
                    variationOptions.add(variationOption);
                }
                productItem.setVariationOptions(variationOptions);
                List<Image> imageItem = new ArrayList<>();
                imageItem.add(imageService.handleToImage(productDto.getProductItem().get(i).getMultipart(), "productitem/"));
                productItem.setImages(imageItem);
                productItem.setProduct(productSaved);
                productItemService.saveModel(productItem);
            }
        }catch (CustomValidationException ex) {
                log.error("Exception occurred validation data input , Exception message {}", ex.getMessage());
                throw ex;
        }catch (Exception ex){
            log.error("Exception occurred while persisting ProductService:saveModel database and server , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.SAVE_FAIL.getLabel(), EResponseStatus.SAVE_FAIL.getCode());
        }

    }

    @Override
    public Product saveModelDto(ProductDto model) {
        log.info("FileEntryService:saveModel(file,directory) execution started.");
        try {
           Product product = productMapper.convertProductDtoToProduct(model);
            System.out.println(product.getName());
        }catch (Exception ex){
            log.error("Exception occurred while persisting ProductService:saveModelDto database and server , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.SAVE_FAIL.getLabel(), EResponseStatus.SAVE_FAIL.getCode());
        }
        return null;
    }

    private String folderUrl() {
        return getClass().getSimpleName().toLowerCase() + "/";
    }

    @Override
    public Product saveModel(Product model) {
        log.info("FileEntryService:saveModel(Product) execution started.");
        try {
            List<Image> images = new ArrayList<>();
            for (Image image: model.getImages()) {
                images.add(imageService.saveModel(image));
            }
            model.setImages(images);
            setMetaData(model);
            setPersonAction(model);
            return productRepository.saveModel(model);
        }catch (Exception ex){
            log.error("Exception occurred while persisting ProductService:saveModel database and server , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.SAVE_FAIL.getLabel(), EResponseStatus.SAVE_FAIL.getCode());
        }
    }
}
