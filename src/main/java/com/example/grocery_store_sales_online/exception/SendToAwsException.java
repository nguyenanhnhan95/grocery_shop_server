package com.example.grocery_store_sales_online.exception;

import com.example.grocery_store_sales_online.enums.EResponseStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendToAwsException extends RuntimeException{
    private int code;
    public SendToAwsException(String message, int code) {
        super(message);
        this.code=code;
    }
}
