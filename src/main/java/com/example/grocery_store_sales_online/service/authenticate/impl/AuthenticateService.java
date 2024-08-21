package com.example.grocery_store_sales_online.service.authenticate.impl;

import com.example.grocery_store_sales_online.config.AppProperties;
import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.exception.CustomValidationException;
import com.example.grocery_store_sales_online.exception.InvalidException;
import com.example.grocery_store_sales_online.exception.ServiceBusinessExceptional;
import com.example.grocery_store_sales_online.model.InvalidatedToken;
import com.example.grocery_store_sales_online.payload.LoginRequest;
import com.example.grocery_store_sales_online.repository.token.InvalidatedTokenRepository;
import com.example.grocery_store_sales_online.security.CustomUserDetailsService;
import com.example.grocery_store_sales_online.security.TokenProvider;
import com.example.grocery_store_sales_online.security.UserPrincipal;
import com.example.grocery_store_sales_online.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.example.grocery_store_sales_online.service.authenticate.IAuthenticateService;
import com.example.grocery_store_sales_online.utils.CommonConstants;
import com.example.grocery_store_sales_online.utils.CookieUtils;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.DecodingException;
import jakarta.servlet.http.Cookie;
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
public class AuthenticateService implements IAuthenticateService {
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
                Claims claims = tokenProvider.validateToken(token);
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
    public void refreshToken(HttpServletRequest request,HttpServletResponse response) {
        try {
            log.info("AuthenticateService:refreshToken execution started.");
            String token = CookieUtils.getJwtFromCookieRequest(request);
            Claims claims=Jwts.parser().setSigningKey(appProperties.getAuth().getTokenSecret()).parseClaimsJws(token).getBody();
            Date dateExpire = claims.getExpiration();
            if (dateExpire.getTime() - new Date().getTime() > CommonConstants.EXPIRE_REFRESH_TOKEN_TIME) {
                return;
            }
            if (invalidatedTokenRepository.findByIdToken((String) claims.get("idToken")).isPresent()) {
                throw new InvalidException(EResponseStatus.UNAUTHENTICATED.getLabel(), EResponseStatus.UNAUTHENTICATED.getCode());
            }
            Date now = new Date();
            String userId = claims.getSubject();
            String provider = (String) claims.get("provider");
            String newToken = Jwts.builder()
                    .setSubject(userId)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(now.getTime() + CommonConstants.REFRESH_TOKEN_TIME))
                    .claim("provider", provider)
                    .claim("idToken", UUID.randomUUID().toString())
                    .signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret())
                    .compact();
            CookieUtils.addCookie(response, CommonConstants.COOKIE_AUTH_TOKEN,newToken,CommonConstants.EXPIRE_TOKEN_COOKIE);
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
            throw new InvalidException(EResponseStatus.UNAUTHENTICATED.getLabel(), EResponseStatus.UNAUTHENTICATED.getCode());
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
            throw new InvalidException(EResponseStatus.EXPIRED_TOKEN.getLabel(), EResponseStatus.EXPIRED_TOKEN.getCode());
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        } catch (DecodingException e) {
            log.error("Decoding error");
        } catch (Exception e) {
            log.error("Decoding error");
            throw new InvalidException(EResponseStatus.REFRESH_TOKEN_FAIL.getLabel(), EResponseStatus.REFRESH_TOKEN_FAIL.getCode());
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
