package com.example.grocery_store_sales_online.service.authenticate.impl;

import com.example.grocery_store_sales_online.config.AppProperties;
import com.example.grocery_store_sales_online.enums.AuthProvider;
import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.exception.CustomValidationException;
import com.example.grocery_store_sales_online.exception.InvalidException;
import com.example.grocery_store_sales_online.payload.LoginRequest;
import com.example.grocery_store_sales_online.repository.token.InvalidatedTokenRepository;
import com.example.grocery_store_sales_online.security.CustomUserDetailsService;
import com.example.grocery_store_sales_online.security.TokenProvider;
import com.example.grocery_store_sales_online.security.UserPrincipal;
import com.example.grocery_store_sales_online.service.authenticate.IAuthenticateService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.DecodingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticateService implements IAuthenticateService {
    private final AppProperties appProperties;
    private final InvalidatedTokenRepository invalidatedTokenRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    @Override
    public String login(LoginRequest loginRequest) {
        try {
            log.info("AuthenticateService:login execution started.");
            UserPrincipal userPrincipal = (UserPrincipal) customUserDetailsService.loadUserByUsername(loginRequest.getName());
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getName(), loginRequest.getPassword(), userPrincipal.getAuthorities()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return tokenProvider.createToken(authentication, AuthProvider.local, loginRequest.isFlagKeep());
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
    public String refreshToken(String token) {
        try {
            log.info("AuthenticateService:refreshToken execution started.");
            Claims claims = Jwts.parser().setSigningKey(appProperties.getAuth().getTokenSecret()).parseClaimsJws(token).getBody();
            if (invalidatedTokenRepository.findByIdToken((String) claims.get("idToken")).isPresent()) {
                throw new InvalidException(EResponseStatus.UNAUTHENTICATED.getLabel(), EResponseStatus.UNAUTHENTICATED.getCode());
            }
            Date now = new Date();
            String userId = claims.getSubject();
            String provider = (String) claims.get("provider");
            return Jwts.builder()
                    .setSubject(userId)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(now.getTime() + 60 * 60 * 1000))
                    .claim("provider", provider)
                    .claim("idToken", UUID.randomUUID().toString())
                    .signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret())
                    .compact();
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
            throw new InvalidException(EResponseStatus.SIGNATURE_TOKEN_FAIL.getLabel(), EResponseStatus.SIGNATURE_TOKEN_FAIL.getCode());
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
            throw new InvalidException(EResponseStatus.TOKEN_NOT_SUPPORT.getLabel(), EResponseStatus.TOKEN_NOT_SUPPORT.getCode());
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
            throw new InvalidException(EResponseStatus.EXPIRED_TOKEN.getLabel(), EResponseStatus.EXPIRED_TOKEN.getCode());
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
            throw new InvalidException(EResponseStatus.TOKEN_NOT_SUPPORT.getLabel(), EResponseStatus.TOKEN_NOT_SUPPORT.getCode());
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
            throw new InvalidException(EResponseStatus.TOKEN_NOT_EMPTY.getLabel(), EResponseStatus.TOKEN_NOT_EMPTY.getCode());
        } catch (Exception e) {
            log.error("Decoding error");
            throw new InvalidException(EResponseStatus.REFRESH_TOKEN_FAIL.getLabel(), EResponseStatus.REFRESH_TOKEN_FAIL.getCode());
        }
    }
}
