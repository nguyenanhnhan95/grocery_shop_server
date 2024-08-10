package com.example.grocery_store_sales_online.handler;

import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.exception.*;
import com.example.grocery_store_sales_online.payload.ApiResponse;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse<?>> handleRunTimeException(RuntimeException exception) {
        ApiResponse<?> apiResponse = new ApiResponse<>();
        apiResponse.setCode(EResponseStatus.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(exception.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }
    @ExceptionHandler(value = IllegalStateException.class)
    ResponseEntity<ApiResponse<?>> handleIllegalStateException(RuntimeException exception) {
        ApiResponse<?> apiResponse = new ApiResponse<>();
        apiResponse.setCode(EResponseStatus.INTERNAL_SERVER_ERROR.getCode());
        apiResponse.setMessage(exception.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    ResponseEntity<ApiResponse<?>> handleHttpMessageNotReadableException(RuntimeException exception) {
        ApiResponse<?> apiResponse = new ApiResponse<>();
        apiResponse.setCode(EResponseStatus.READABLE_FROM_HTTP.getCode());
        apiResponse.setMessage(exception.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    ResponseEntity<ApiResponse<?>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception){
        ApiResponse<?> apiResponse = new ApiResponse<>();
        apiResponse.setCode(EResponseStatus.METHOD_ARGUMENT_MVC.getCode());
        apiResponse.setMessage(exception.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }
    @ExceptionHandler(value = ConstraintViolationException.class)
    ResponseEntity<ApiResponse<?>> handleConstrainViolationException(ConstraintViolationException exception) {
        ApiResponse<?> apiResponse = new ApiResponse<>();
        apiResponse.setCode(EResponseStatus.VIOLATION_CONSTRAIN.getCode());
        apiResponse.setMessage(EResponseStatus.VIOLATION_CONSTRAIN.getLabel());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse<?>> handleAppException(AppException exception) {
        ApiResponse<?> apiResponse = new ApiResponse<>();
        EResponseStatus responseStatus = exception.getResponseStatus();
        apiResponse.setCode(responseStatus.getCode());
        apiResponse.setMessage(responseStatus.getLabel());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    ResponseEntity<ApiResponse<?>> handleResourceNotFoundException(ResourceNotFoundException exception) {
        ApiResponse<?> apiResponse = new ApiResponse<>();
        EResponseStatus responseStatus = exception.getResponseStatus();
        apiResponse.setCode(responseStatus.getCode());
        apiResponse.setMessage(responseStatus.getLabel());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @ExceptionHandler(value = BindException.class)
    ResponseEntity<ApiResponse<Map<String, String>>> handleBindException(BindException e) {
        ApiResponse<Map<String, String>> apiResponse = new ApiResponse<>();
        apiResponse.setCode(EResponseStatus.VIOLATION_CONSTRAIN.getCode());
        apiResponse.setMessage(EResponseStatus.VIOLATION_CONSTRAIN.getLabel());
        Map<String, String> errors = new HashMap<>();
        if (e.getBindingResult().hasErrors()) {
            for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
        }
        apiResponse.setResult(errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    ResponseEntity<ApiResponse<Map<String, String>>> handleBadCredential(BadCredentialsException e) {
        ApiResponse<Map<String, String>> apiResponse = new ApiResponse<>();
        apiResponse.setCode(EResponseStatus.VIOLATION_CONSTRAIN.getCode());
        apiResponse.setMessage(e.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("password", e.getMessage());
        apiResponse.setResult(errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }


    @ExceptionHandler(value = UsernameNotFoundException.class)
    ResponseEntity<ApiResponse<Map<String, String>>> handleUsernameNotFoundException(UsernameNotFoundException e) {
        ApiResponse<Map<String, String>> apiResponse = new ApiResponse<>();
        apiResponse.setCode(EResponseStatus.USER_EXISTED.getCode());
        apiResponse.setMessage(EResponseStatus.USER_NOT_FOUND.getLabel());
        Map<String, String> errors = new HashMap<>();
        errors.put("password", "Tài khoản hoặc mật khẩu không tồn tại");
        apiResponse.setResult(errors);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @ExceptionHandler(value = AuthenticationException.class)
    ResponseEntity<ApiResponse<?>> handleActiveException(AuthenticationException exception) {
        ApiResponse<?> apiResponse = new ApiResponse<>();
        apiResponse.setCode(4001);
        apiResponse.setMessage(exception.getMessage());
        return ResponseEntity.status(HttpStatus.LOCKED).body(apiResponse);
    }

    @ExceptionHandler(value = InvalidException.class)
    ResponseEntity<ApiResponse<?>> handleInvalidException(InvalidException exception) {
        ApiResponse<?> apiResponse = new ApiResponse<>();
        apiResponse.setCode(exception.getCode());
        apiResponse.setMessage(exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResponse);
    }

    @ExceptionHandler(value = ExpiredJwtException.class)
    ResponseEntity<ApiResponse<?>> handleAccessDeniedException(ExpiredJwtException exception) {
        ApiResponse<?> apiResponse = new ApiResponse<>();
        apiResponse.setCode(100000000);
        apiResponse.setMessage(exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiResponse);
    }

    @ExceptionHandler(ServiceBusinessExceptional.class)
    ResponseEntity<ApiResponse<?>> handleServiceException(ServiceBusinessExceptional exception) {
        ApiResponse<?> apiResponse = new ApiResponse<>();
        apiResponse.setCode(exception.getCode());
        apiResponse.setMessage(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<?>>  handleMethodArgumentException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        ApiResponse<?> apiResponse = new ApiResponse<>(EResponseStatus.ENTER_DATA_FAIL.getCode(), EResponseStatus.ENTER_DATA_FAIL.getLabel(), errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }
    @ExceptionHandler(CustomValidationException.class)
    ResponseEntity<ApiResponse<?>>  handleCustomValidationException(CustomValidationException exception) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        ApiResponse<?> apiResponse = new ApiResponse<>(EResponseStatus.ENTER_DATA_FAIL.getCode(), EResponseStatus.ACCOUNT_NOT_EXISTING.getLabel(), errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }
}
