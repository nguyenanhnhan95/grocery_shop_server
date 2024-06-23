package com.example.grocery_store_sales_online.exception;

import com.example.grocery_store_sales_online.enums.EResponseStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.BindingResult;
@Getter
@Setter
public class CustomValidationException extends RuntimeException{
    private EResponseStatus responseStatus;
    private final BindingResult bindingResult;
    public CustomValidationException(BindingResult bindingResult,EResponseStatus responseStatus) {
        super("Lỗi validation");
        this.bindingResult = bindingResult;
        this.responseStatus=responseStatus;
    }

}
