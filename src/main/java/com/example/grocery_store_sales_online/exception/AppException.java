package com.example.grocery_store_sales_online.exception;

import com.example.grocery_store_sales_online.enums.EResponseStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppException extends RuntimeException{
    private EResponseStatus responseStatus;

    public AppException(EResponseStatus responseStatus) {
        super(responseStatus.getLabel());
        this.responseStatus = responseStatus;
    }
}
