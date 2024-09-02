package com.example.grocery_store_sales_online.service.impl;

import com.example.grocery_store_sales_online.config.AppProperties;
import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.exception.CustomValidationException;
import com.example.grocery_store_sales_online.exception.ServiceBusinessExceptional;
import com.example.grocery_store_sales_online.model.InvalidatedToken;
import com.example.grocery_store_sales_online.payload.LoginRequest;
import com.example.grocery_store_sales_online.repository.token.InvalidatedTokenRepository;
import com.example.grocery_store_sales_online.security.CustomUserDetailsService;
import com.example.grocery_store_sales_online.security.TokenProvider;
import com.example.grocery_store_sales_online.security.UserPrincipal;
import com.example.grocery_store_sales_online.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.example.grocery_store_sales_online.service.IAuthenticateService;
import com.example.grocery_store_sales_online.utils.CommonConstants;
import com.example.grocery_store_sales_online.utils.CookieUtils;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticateServiceImpl implements IAuthenticateService {
    private final AppProperties appProperties;
    private final InvalidatedTokenRepository invalidatedTokenRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Override
    public String login(LoginRequest loginRequest, HttpServletResponse response) {
        try {
            log.info("AuthenticateService:login execution started.");
            UserPrincipal userPrincipal = (UserPrincipal) customUserDetailsService.loadUserByUsername(loginRequest.getNameLogin());
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getNameLogin(), loginRequest.getPassword(), userPrincipal.getAuthorities()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token= tokenProvider.createToken(authentication,  loginRequest.isFlagKeep());
            CookieUtils.addCookie(response, CommonConstants.COOKIE_AUTH_TOKEN,token,7 * 24 * 60 * 60);

            return token;
        } catch (DisabledException e) {
            BindingResult bindingResult = new BeanPropertyBindingResult(loginRequest, "loginRequest");
            bindingResult.addError(new FieldError("loginRequest", "password", EResponseStatus.ACCOUNT_DISABLE.getLabel()));
            throw new CustomValidationException(bindingResult, EResponseStatus.ACCOUNT_DISABLE.getCode());
        } catch (BadCredentialsException e) {
            BindingResult bindingResult = new BeanPropertyBindingResult(loginRequest, "loginRequest");
            bindingResult.addError(new FieldError("loginRequest", "password", EResponseStatus.ACCOUNT_NOT_EXISTING.getLabel()));
            throw new CustomValidationException(bindingResult, EResponseStatus.ACCOUNT_NOT_EXISTING.getCode());
        } catch (Exception ex){
            BindingResult bindingResult = new BeanPropertyBindingResult(loginRequest, "loginRequest");
            bindingResult.addError(new FieldError("loginRequest", "password", EResponseStatus.ACCOUNT_NOT_EXISTING.getLabel()));
            throw new CustomValidationException(bindingResult, EResponseStatus.LOGIN_FAIL.getCode());
        }
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            log.info("AuthenticateService:logout execution started.");
            String token = CookieUtils.getJwtFromCookieRequest(request);
            if (StringUtils.hasText(token)) {
                Claims claims = tokenProvider.validateToken(token,request,response);
                String idToken = (String) claims.get("idToken");
                Date expireDate = claims.getExpiration();
                InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                        .idToken(idToken)
                        .expiryTime(expireDate)
                        .build();
                invalidatedTokenRepository.save(invalidatedToken);
                httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
                CookieUtils.deleteCookie(request,response,CommonConstants.COOKIE_AUTH_TOKEN);
            } else {
                throw new ServiceBusinessExceptional(EResponseStatus.TOKEN_NOT_VALIDATE.getLabel(), EResponseStatus.TOKEN_NOT_VALIDATE.getCode());
            }
        }catch (ServiceBusinessExceptional ex){
            log.error("Exception occurred while persisting AuthenticateService:logout logout fail , Exception message {}", ex.getMessage());
            throw ex;
        }catch (Exception ex){
            log.error("Exception occurred while persisting AuthenticateService:logout logout fail , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.LOGOUT_FAIL.getLabel(), EResponseStatus.LOGOUT_FAIL.getCode());
        }
    }



    @Override
    public List<String> getRoleAuthorize(UserPrincipal userPrincipal) {
        try {
            log.info("AuthenticateService:getRoleAuthorize execution started.");
            return userPrincipal.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
        }catch (Exception ex){
            log.error("Exception occurred while persisting AuthenticateService:getRoleAuthorize to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    public void checkAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            throw new ServiceBusinessExceptional(
                    EResponseStatus.UNAUTHENTICATED.getLabel(),
                    EResponseStatus.UNAUTHENTICATED.getCode()
            );
        }
    }

}
