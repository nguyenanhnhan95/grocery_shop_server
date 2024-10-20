package com.example.grocery_store_sales_online.security;

import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final Logger logger = LoggerFactory.getLogger(RestAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        final Map<String, Object> body = new HashMap<>();
        if(response.getStatus()!=HttpServletResponse.SC_UNAUTHORIZED){
            body.put("code",response.getStatus());
            body.put("payload", authException.getMessage());
        }else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            body.put("code",response.getStatus());
            body.put("payload", EResponseStatus.UNAUTHENTICATED.getLabel());
        }
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }
}
