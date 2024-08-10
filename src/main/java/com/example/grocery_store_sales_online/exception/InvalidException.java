package com.example.grocery_store_sales_online.exception;

import com.example.grocery_store_sales_online.enums.EResponseStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InvalidException extends RuntimeException {
    private int code;
    private String message;

    public InvalidException(String message,int code) {
        super(message);
        this.message = message;
        this.code=code;
    }
}
