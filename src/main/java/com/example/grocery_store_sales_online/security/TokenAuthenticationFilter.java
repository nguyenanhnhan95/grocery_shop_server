package com.example.grocery_store_sales_online.security;

import com.example.grocery_store_sales_online.enums.AuthProvider;
import com.example.grocery_store_sales_online.enums.ErrorCode;
import com.example.grocery_store_sales_online.exception.InvalidException;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.io.PrintWriter;

public class TokenAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private  TokenProvider tokenProvider;
    private HandlerExceptionResolver exceptionResolver;

    @Autowired
    private  CustomUserDetailsService customUserDetailsService;

    public TokenAuthenticationFilter(HandlerExceptionResolver exceptionResolver) {
        this.exceptionResolver = exceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);
            Claims claims = tokenProvider.validateToken(jwt);
            if (StringUtils.hasText(jwt) && claims!=null ) {
                Long userId = Long.valueOf(claims.getSubject());
                String provider = (String) claims.get("provider");
                UserDetails userDetails = null;
                if (AuthProvider.local.toString().equals(provider)) {
                    userDetails = customUserDetailsService.loadEmployeeById(userId);
                } else {
                    userDetails = customUserDetailsService.loadUserById(userId);
                }
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context"+ex.getMessage());
            if(ex instanceof InvalidException){
                InvalidException invalidException = (InvalidException) ex;
                response.setStatus(invalidException.getErrorCode().getCode());
            }else {
                response.setStatus(ErrorCode.UNAUTHENTICATED.getCode());
            }

        }
        filterChain.doFilter(request, response);
    }
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

}
