package com.example.grocery_store_sales_online.exception;

import lombok.Getter;
import lombok.Setter;


public class EmptyException extends RuntimeException {
    public EmptyException(String message) {
        super(message);
    }
}
