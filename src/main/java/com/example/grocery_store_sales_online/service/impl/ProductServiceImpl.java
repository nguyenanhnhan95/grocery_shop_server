package com.example.grocery_store_sales_online.service.impl;

import com.example.grocery_store_sales_online.dto.product.ProductDto;
import com.example.grocery_store_sales_online.dto.product.ProductEditDto;
import com.example.grocery_store_sales_online.dto.product.ProductItemDto;
import com.example.grocery_store_sales_online.dto.product.ProductItemEditDto;
import com.example.grocery_store_sales_online.enums.EResponseStatus;

import com.example.grocery_store_sales_online.exception.CustomValidationException;
import com.example.grocery_store_sales_online.exception.EmptyException;
import com.example.grocery_store_sales_online.exception.MapperException;
import com.example.grocery_store_sales_online.exception.ServiceBusinessExceptional;
import com.example.grocery_store_sales_online.custom.mapper.product.ProductItemMapper;
import com.example.grocery_store_sales_online.custom.mapper.product.ProductMapper;
import com.example.grocery_store_sales_online.model.File.Image;
import com.example.grocery_store_sales_online.model.product.*;
import com.example.grocery_store_sales_online.projection.product.ProductItemProjection;
import com.example.grocery_store_sales_online.projection.product.ProductProjection;
import com.example.grocery_store_sales_online.repository.Promotion.impl.PromotionRepository;
import com.example.grocery_store_sales_online.repository.product.impl.ProductRepository;
import com.example.grocery_store_sales_online.repository.productCategory.impl.ProductCategoryRepository;
import com.example.grocery_store_sales_online.repository.productItem.impl.ProductItemRepository;
import com.example.grocery_store_sales_online.repository.variation.VariationRepository;
import com.example.grocery_store_sales_online.repository.variationOption.VariationOptionRepository;
import com.example.grocery_store_sales_online.service.IImageService;
import com.example.grocery_store_sales_online.service.IProductItemService;
import com.example.grocery_store_sales_online.service.IProductService;
import com.example.grocery_store_sales_online.utils.CommonConstants;
import com.example.grocery_store_sales_online.utils.ProcessImage;
import com.example.grocery_store_sales_online.utils.QueryListResult;
import com.example.grocery_store_sales_online.utils.QueryParameter;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.grocery_store_sales_online.utils.CommonConstants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl extends BaseServiceImpl implements IProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final IImageService imageService;
    private final ProductItemMapper productItemMapper;

    private final IProductItemService productItemService;

    @Override
    public QueryListResult<ProductProjection> getListResult(String queryParameter) {
        try {
            log.info("ProductService:getListResult execution started.");
            return productRepository.getListResult(readJsonQuery(queryParameter, QueryParameter.class));
        } catch (Exception ex) {
            log.error("Exception occurred while persisting ProductService:getListResult to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    public void saveProductDto(ProductDto productDto, HttpServletRequest request) {
        try {
            log.info("ProductService:saveProductDto execution started.");
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

            Map<String, List<MultipartFile>> productItemsImages = new HashMap<>();
            List<MultipartFile> productImages = new ArrayList<>();
            multipartRequest.getMultiFileMap().forEach((key, files) -> {
                if (key.startsWith("productItems[")) {
                    productItemsImages.put(key, files);
                } else if (key.equals("product")) {
                    productImages.addAll(files);
                }
            });

            if (productImages.isEmpty()) {
                throw createValidationException("ProductDto", "images", CommonConstants.THIS_UPLOAD_FILE_ITEM_CANNOT_EMPTY);
            }else {
                this.validateImages("ProductDto", "images",productImages );
            }
            productDto.setImages(productImages);
            int sizeProductItems = productDto.getProductItems().size();
            int sizeProductItemsImage = productItemsImages.size();
            if (sizeProductItems == 0) {
                throw createValidationException("ProductDto", "productItems[0].images", CommonConstants.THIS_UPLOAD_FILE_ITEM_CANNOT_EMPTY);
            }
            for (int i = 0; i < sizeProductItems; i++) {
                if (i < sizeProductItemsImage) {
                    this.validateImages("ProductDto", "productItems[" + i + "].images", productItemsImages.get("productItems[" + i + "].images"));
                } else {
                    throw createValidationException("ProductDto", "productItems["+i+"].images", CommonConstants.THIS_UPLOAD_FILE_ITEM_CANNOT_EMPTY);
                }
                productDto.getProductItems().get(i).setImages(productItemsImages.get("productItems[" + i + "].images"));

            }
            Product product =productMapper.convertProductDtoToProduct(productDto);
            List<Image> images = new ArrayList<>(imageService.handleToImages(productDto.getImages(), "product/"));
            product.setImages(images);
            setMetaData(product);
            setPersonAction(product);
            Product productSaved= productRepository.saveModel(product);
            for (ProductItemDto each :productDto.getProductItems()) {
                productItemService.saveModelDto(each,productSaved);
            }
        } catch (CustomValidationException ex) {
            log.error("Exception occurred validation data input , Exception message {}", ex.getMessage());
            throw ex;
        } catch (MapperException ex) {
            log.error("Exception occurred product mapper data input , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.MAPPER_DATA_FAIL.getLabel(), EResponseStatus.MAPPER_DATA_FAIL.getCode());
        } catch (Exception ex) {
            log.error("Exception occurred while persisting ProductService:saveModel database and server , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.SAVE_FAIL.getLabel(), EResponseStatus.SAVE_FAIL.getCode());
        }

    }

    @Override
    public void editProductDto(Long id, ProductEditDto productEditDto, HttpServletRequest request) {
        try {
            log.info("ProductService:saveProductDto execution started.");
            Product productOlder = findById(id);
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

            Map<String, List<MultipartFile>> productItemsImages = new HashMap<>();
            List<MultipartFile> productImages = new ArrayList<>();
            multipartRequest.getMultiFileMap().forEach((key, files) -> {
                if (key.startsWith("productItems[")) {
                    productItemsImages.put(key, files);
                } else if (key.equals("product")) {
                    productImages.addAll(files);
                }
            });

            if (productImages.isEmpty()) {
                throw createValidationException("ProductDto", "images", CommonConstants.THIS_UPLOAD_FILE_ITEM_CANNOT_EMPTY);
            } else {
                this.validateImages("ProductDto", "images", productImages);
            }
            productEditDto.setImages(productImages);
            int sizeProductItems = productEditDto.getProductItems().size();
            int sizeProductItemsImage = productItemsImages.size();
            if (sizeProductItems == 0) {
                throw createValidationException("ProductDto", "productItems[0].images", CommonConstants.THIS_UPLOAD_FILE_ITEM_CANNOT_EMPTY);
            }
            for (int i = 0; i < sizeProductItems; i++) {
                if (i < sizeProductItemsImage) {
                    this.validateImages("ProductDto", "productItems[" + i + "].images", productItemsImages.get("productItems[" + i + "].images"));
                } else {
                    throw createValidationException("ProductDto", "productItems[" + i + "].images", CommonConstants.THIS_UPLOAD_FILE_ITEM_CANNOT_EMPTY);
                }
                productEditDto.getProductItems().get(i).setImages(productItemsImages.get("productItems[" + i + "].images"));

            }
            Product product = productMapper.updateProductDtoToProduct(productEditDto,productOlder);
            List<Image> images = new ArrayList<>(imageService.handleToImages(productEditDto.getImages(), "product/"));
            product.setImages(images);
            setMetaData(product);
            setPersonAction(product);
            Product productSaved = productRepository.saveModel(product);
            for (ProductItemEditDto each : productEditDto.getProductItems()) {
                productItemService.updateModelDto(each, productSaved);
            }
        }catch (EmptyException ex){
            throw ex;
        } catch (CustomValidationException ex) {
            log.error("Exception occurred validation data input , Exception message {}", ex.getMessage());
            throw ex;
        } catch (MapperException ex) {
            log.error("Exception occurred product mapper data input , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.MAPPER_DATA_FAIL.getLabel(), EResponseStatus.MAPPER_DATA_FAIL.getCode());
        } catch (Exception ex) {
            log.error("Exception occurred while persisting ProductService:saveModel database and server , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.SAVE_FAIL.getLabel(), EResponseStatus.SAVE_FAIL.getCode());
        }
    }

    @Override
    public ProductProjection findByIdProductEdit(Long id) {
        try {
            log.info("ProductService:findByIdProductEdit execution started.");
            ProductProjection productProjection= productRepository.findByIdFormEdit(id)
                    .orElseThrow(() ->
                            new EmptyException(
                                    EResponseStatus.DATA_EMPTY.getLabel()
                            )
                    );
            List<ProductItemProjection> productItemProjections = productItemService.findByIdProductAble(id);
            productProjection.setProductItems(productItemProjections);
            return productProjection;
        } catch (EmptyException ex) {
            throw ex;
        }catch (Exception ex){
            log.error("Exception occurred while persisting ProductService:findByIdProductEdit to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }
    @Override
    public Product findById(Long id) {
        try {
            log.info("ProductService:findById execution started.");
            return productRepository.findById(id)
                    .orElseThrow(() ->
                            new EmptyException(
                                    EResponseStatus.DATA_EMPTY.getLabel()
                            )
                    );
        } catch (EmptyException ex) {
            throw ex;
        }catch (Exception ex){
            log.error("Exception occurred while persisting ProductService:findById to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    private String folderUrl() {
        return getClass().getSimpleName().toLowerCase() + "/";
    }

    @Override
    public Product saveModel(Product model) {
        log.info("FileEntryService:saveModel(Product) execution started.");
        try {
            List<Image> images = new ArrayList<>();
            for (Image image : model.getImages()) {
                images.add(imageService.saveModel(image));
            }
            model.setImages(images);
            setMetaData(model);
            setPersonAction(model);
            return productRepository.saveModel(model);
        } catch (Exception ex) {
            log.error("Exception occurred while persisting ProductService:saveModel database and server , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.SAVE_FAIL.getLabel(), EResponseStatus.SAVE_FAIL.getCode());
        }
    }


}
