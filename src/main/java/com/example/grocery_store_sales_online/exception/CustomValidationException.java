package com.example.grocery_store_sales_online.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.BindingResult;
@Getter
@Setter
public class CustomValidationException extends RuntimeException{
    private int code;
    private final BindingResult bindingResult;
    public CustomValidationException(BindingResult bindingResult,int code) {
        super("Lỗi validation");
        this.bindingResult = bindingResult;
        this.code=code;
    }

}
