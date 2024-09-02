package com.example.grocery_store_sales_online.service.impl;

import com.example.grocery_store_sales_online.dto.product.ProductItemDto;
import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.exception.ServiceBusinessExceptional;
import com.example.grocery_store_sales_online.mapper.product.ProductItemMapper;
import com.example.grocery_store_sales_online.model.File.Image;
import com.example.grocery_store_sales_online.model.product.ProductItem;
import com.example.grocery_store_sales_online.repository.image.impl.ImageRepository;
import com.example.grocery_store_sales_online.repository.productItem.impl.ProductItemRepository;
import com.example.grocery_store_sales_online.service.IProductItemService;
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
    private final ImageRepository imageService;

    @Override
    public ProductItem saveModelDto(ProductItemDto modelDto) {
        ProductItem productItem = productItemMapper.convertProductItemDtoToProductItem(modelDto);
        return null;
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
}
