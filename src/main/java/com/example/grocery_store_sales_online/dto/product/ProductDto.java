package com.example.grocery_store_sales_online.dto.product;

import com.example.grocery_store_sales_online.custom.validation.ListNotEmptyConstraint;
import com.example.grocery_store_sales_online.custom.validation.ProductCategoryConstraint;
import com.example.grocery_store_sales_online.custom.validation.SizeStringConstraint;
import com.example.grocery_store_sales_online.custom.validation.VariationConstraint;
import com.example.grocery_store_sales_online.utils.CommonConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.example.grocery_store_sales_online.utils.CommonConstants.*;

@Getter
@Setter
public class ProductDto {
    private List<MultipartFile> images;
    //        @Size(max = 10,message = THIS_FIELD_TOO_LONG)
//        @NotBlank(message = THIS_FIELD_CANNOT_EMPTY_2)
    @SizeStringConstraint(sizeMin = 2, sizeMax = 100)
    @NotBlank(message = THIS_FIELD_CANNOT_EMPTY_2)
    private String name;
    @SizeStringConstraint(sizeMin = 2, sizeMax = 100)
    @Pattern(regexp = REGEX_CHARACTER_VI, message = CommonConstants.THIS_FIELD_VALUE_NOT_FORMAT)
    private String brand;
    @ProductCategoryConstraint
    @NotNull(message = THIS_COMBOBOX_ITEM_CANNOT_EMPTY)
    private Long productCategory;
    @VariationConstraint
    @NotNull(message = THIS_COMBOBOX_ITEM_CANNOT_EMPTY)
    private Long variation;
    @SizeStringConstraint(sizeMin = 2, sizeMax = 150)
    private String description;
    @ListNotEmptyConstraint
    private List<@Valid ProductItemDto> productItems = new ArrayList<>();
}
