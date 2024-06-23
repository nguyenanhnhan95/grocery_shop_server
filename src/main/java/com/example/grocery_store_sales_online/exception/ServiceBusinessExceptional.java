package com.example.grocery_store_sales_online.exception;

import com.example.grocery_store_sales_online.enums.EResponseStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ServiceBusinessExceptional extends RuntimeException{
    private EResponseStatus responseStatus;
    public ServiceBusinessExceptional(String message, EResponseStatus responseStatus) {
        super(message);
        this.responseStatus=responseStatus;
    }
}
