package com.example.grocery_store_sales_online.dto.product;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.example.grocery_store_sales_online.utils.CommonConstants.THIS_FIELD_CANNOT_EMPTY_2;
import static com.example.grocery_store_sales_online.utils.CommonConstants.THIS_FIELD_TOO_LONG;

@Getter
@Setter
public class ProductDto {
    private List<MultipartFile> multipart;
    //        @Size(max = 10,message = THIS_FIELD_TOO_LONG)
//        @NotBlank(message = THIS_FIELD_CANNOT_EMPTY_2)
    @Size(max = 150, message = THIS_FIELD_TOO_LONG)
    @NotBlank(message = THIS_FIELD_CANNOT_EMPTY_2)
    private String name;
    private String brand;
    private Long idProductCategory;
    private Long idVariation;
    private String description;

    private List<@Valid ProductItemDto> productItem = new ArrayList<>();
}
