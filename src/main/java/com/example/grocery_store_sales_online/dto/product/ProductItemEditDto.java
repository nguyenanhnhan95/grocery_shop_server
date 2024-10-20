package com.example.grocery_store_sales_online.dto.product;

import com.example.grocery_store_sales_online.custom.validation.ListNotEmptyConstraint;
import com.example.grocery_store_sales_online.custom.validation.PromotionConstraint;
import com.example.grocery_store_sales_online.custom.validation.VariationOptionConstraint;
import com.example.grocery_store_sales_online.utils.CommonConstants;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Getter
@Setter
public class ProductItemEditDto {
    private Long id;
    private List<MultipartFile> images;
    @Max(value = 1000000000,message = CommonConstants.THIS_FILE_SIZE_TOO_LARGE)
    private Long price;
    @NotBlank(message = CommonConstants.THIS_FIELD_CANNOT_EMPTY_2)
    private String sku;
    private Long qtyInStock;
    @PromotionConstraint
    @ListNotEmptyConstraint
    private List<Long> promotions;
    @VariationOptionConstraint
    private List<Long> variationOptions;
}
