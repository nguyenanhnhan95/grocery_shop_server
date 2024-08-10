package com.example.grocery_store_sales_online.dto.shop;


import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import java.util.Date;

import static com.example.grocery_store_sales_online.utils.CommonConstants.*;

@Getter
@Setter
public class PromotionDto {
    @Size(max = 100, message = THIS_FIELD_TOO_LONG)
    @Size(min = 3, message = THIS_FIELD_TOO_SHORT)
    @NotBlank(message = THIS_FIELD_CANNOT_EMPTY_2)
    private String name;

    @NotBlank(message = THIS_FIELD_CANNOT_EMPTY_2)
    private String code;
    @Size(max = 300, message = THIS_FIELD_TOO_LONG)
    @Size(min = 3, message = THIS_FIELD_TOO_SHORT)
    private String description;
    @Min(value = 1,message =THIS_FIELD_VALUE_NOT_FORMAT )
    @Max(value = 100,message = THIS_FIELD_VALUE_NOT_FORMAT)
    private Double discountRate;
    @NotNull(message = THIS_FIELD_CANNOT_EMPTY_2)
    private Date startDate;
    //    @NotBlank(message = EValidationDto.NOT_BLANK.)

    private Date endDate;
}
