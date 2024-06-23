package com.example.grocery_store_sales_online.dto.shop;

import com.example.grocery_store_sales_online.enums.EValidationDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class PromotionDto {
    private String name;
    private String code;
    private String description;
    private double discountRate;
    private Date startDate;
//    @NotBlank(message = EValidationDto.NOT_BLANK.)
    private Date endDate;
}
