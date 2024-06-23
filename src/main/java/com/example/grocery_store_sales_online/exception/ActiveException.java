package com.example.grocery_store_sales_online.exception;

import com.example.grocery_store_sales_online.enums.ErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActiveException extends RuntimeException{
    private ErrorCode errorCode;

    public ActiveException(ErrorCode errorCode) {
        super(errorCode.getLabel());
        this.errorCode = errorCode;
    }
}
