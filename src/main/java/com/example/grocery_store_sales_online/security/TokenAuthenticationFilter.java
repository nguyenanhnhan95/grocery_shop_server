package com.example.grocery_store_sales_online.security;

import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.exception.InvalidException;
import com.example.grocery_store_sales_online.model.person.SocialProvider;
import com.example.grocery_store_sales_online.service.ISocialProviderService;
import com.example.grocery_store_sales_online.utils.CommonConstants;
import com.example.grocery_store_sales_online.utils.CookieUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Optional;

public class TokenAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private  TokenProvider tokenProvider;
    private HandlerExceptionResolver exceptionResolver;
    @Autowired
    private  ISocialProviderService socialProviderService;


    public TokenAuthenticationFilter(HandlerExceptionResolver exceptionResolver) {
        this.exceptionResolver = exceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = CookieUtils.getJwtFromCookieRequest(request);
            Claims claims = tokenProvider.validateToken(jwt,request,response);
            if (StringUtils.hasText(jwt) && claims!=null ) {
                UserDetails userDetails;
                Optional<SocialProvider> socialProvider = socialProviderService.findByProviderId(claims.getSubject());
                if(socialProvider.get().getEmployee()!=null){
                    userDetails=UserPrincipal.createEmployee(socialProvider.get().getEmployee(),claims.getSubject(),socialProvider.get().getEmployee().getRoles());
                }else {
                    userDetails=UserPrincipal.createUser(socialProvider.get().getUser(),claims.getSubject(),"ROLE_USER");
                }
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context"+ex.getMessage());

            if(ex instanceof InvalidException invalidException){
                if(((InvalidException) ex).getCode()!=EResponseStatus.REFRESH_TOKEN.getCode()){
                    CookieUtils.deleteCookie(request,response,CommonConstants.COOKIE_AUTH_TOKEN);
                }
                response.setStatus(invalidException.getCode());
            }else {
                response.setStatus(EResponseStatus.UNAUTHENTICATED.getCode());
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
