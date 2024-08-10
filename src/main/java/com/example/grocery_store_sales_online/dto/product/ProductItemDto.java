package com.example.grocery_store_sales_online.dto.product;

import com.example.grocery_store_sales_online.utils.CommonConstants;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.example.grocery_store_sales_online.utils.CommonConstants.THIS_FIELD_CANNOT_EMPTY_2;

@Getter
@Setter
public class ProductItemDto {
    private MultipartFile multipart;
    @Max(value = 1000000000,message = CommonConstants.THIS_FILE_SIZE_TOO_LARGE)
    private Long price;
    @NotBlank(message = CommonConstants.THIS_FIELD_CANNOT_EMPTY_2)
    private String sky;
    private Long qtyInStock;
    private List<Long> idPromotions;
    private List<Long> idVariationOptions;
}
