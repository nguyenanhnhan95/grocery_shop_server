package com.example.grocery_store_sales_online.exception;

import com.example.grocery_store_sales_online.enums.ErrorCode;
import io.jsonwebtoken.ClaimJwtException;
import jakarta.servlet.ServletException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class InvalidException extends RuntimeException {
    private ErrorCode errorCode;
    private String message;

    public InvalidException(String message,ErrorCode errorCode) {
        super(message);
        this.message = message;
        this.errorCode=errorCode;
    }
}
