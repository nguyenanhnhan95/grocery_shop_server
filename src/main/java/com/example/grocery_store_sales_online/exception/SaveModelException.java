package com.example.grocery_store_sales_online.exception;

import com.example.grocery_store_sales_online.enums.EResponseStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SaveModelException extends RuntimeException{
    private int code;
    public SaveModelException(String message, int code) {
        super(message);
        this.code=code;
    }
}
