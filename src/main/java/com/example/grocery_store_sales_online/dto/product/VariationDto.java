package com.example.grocery_store_sales_online.dto.product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import static com.example.grocery_store_sales_online.utils.CommonConstants.*;

@Getter
@Setter
public class VariationDto {
    @Size(max = 150,message = THIS_FIELD_TOO_LONG)
    @NotBlank(message = THIS_FIELD_CANNOT_EMPTY_2)
    private String name;
    @Size(max = 250,message = THIS_FIELD_TOO_LONG)
    private String description;

}
