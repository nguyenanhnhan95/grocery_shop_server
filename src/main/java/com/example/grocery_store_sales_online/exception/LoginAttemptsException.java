package com.example.grocery_store_sales_online.exception;

import org.springframework.security.core.AuthenticationException;

public class LoginAttemptsException extends AuthenticationException {
    public LoginAttemptsException(String msg) {
        super(msg);
    }
}
