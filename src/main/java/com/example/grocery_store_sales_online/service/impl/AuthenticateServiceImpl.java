package com.example.grocery_store_sales_online.service.impl;


import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.exception.CustomValidationException;
import com.example.grocery_store_sales_online.exception.LoginAttemptsException;
import com.example.grocery_store_sales_online.exception.ServiceBusinessExceptional;
import com.example.grocery_store_sales_online.model.InvalidatedToken;
import com.example.grocery_store_sales_online.model.person.Role;

import com.example.grocery_store_sales_online.model.person.User;
import com.example.grocery_store_sales_online.payload.LoginRequest;

import com.example.grocery_store_sales_online.repository.token.InvalidatedTokenRepository;
import com.example.grocery_store_sales_online.repository.user.impl.UserRepository;
import com.example.grocery_store_sales_online.security.CustomUserDetailsService;
import com.example.grocery_store_sales_online.security.TokenProvider;
import com.example.grocery_store_sales_online.security.UserPrincipal;
import com.example.grocery_store_sales_online.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.example.grocery_store_sales_online.service.IAuthenticateService;
import com.example.grocery_store_sales_online.utils.CommonConstants;
import com.example.grocery_store_sales_online.utils.CookieUtils;
import com.example.grocery_store_sales_online.utils.SessionUtils;
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

import static com.example.grocery_store_sales_online.utils.CommonConstants.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticateServiceImpl extends BaseServiceImpl implements IAuthenticateService {
    private final InvalidatedTokenRepository invalidatedTokenRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Override
    public String login(LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        try {
            log.info("AuthenticateService:login execution started.");
            this.attemptLogin(request);
            UserPrincipal userPrincipal = (UserPrincipal) customUserDetailsService.loadUserByUsername(loginRequest.getNameLogin());
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getNameLogin(), loginRequest.getPassword(), userPrincipal.getAuthorities()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = tokenProvider.createToken(authentication, loginRequest.isFlagKeep());
            CookieUtils.addCookie(response, CommonConstants.COOKIE_AUTH_TOKEN, token, EXPIRE_TOKEN_COOKIE);
            SessionUtils.removeSessionAttribute(request, SESSION_ATTEMPT_LOGIN);
            CookieUtils.addCookie(response, COOKIE_THEME,userPrincipal.getScreenTheme().getLabel().toLowerCase().trim(),EXPIRE_THEME_COOKIE);
            return token;
        } catch (LoginAttemptsException ex) {
            throw new ServiceBusinessExceptional(EResponseStatus.ATTEMPT_LOGIN.getLabel(), EResponseStatus.ATTEMPT_LOGIN.getCode());
        } catch (DisabledException e) {
            handleFailedLoginAttempt(request);
            throw new ServiceBusinessExceptional(EResponseStatus.ACCOUNT_DISABLE.getLabel(), EResponseStatus.ACCOUNT_DISABLE.getCode());
        } catch (BadCredentialsException e) {
            handleFailedLoginAttempt(request);
            throw createCustomValidationException(loginRequest, "password", EResponseStatus.ACCOUNT_NOT_EXISTING);
        } catch (Exception e) {
            handleFailedLoginAttempt(request);
            throw createCustomValidationException(loginRequest, "password", EResponseStatus.LOGIN_FAIL);
        }
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            log.info("AuthenticateService:logout execution started.");
            String token = CookieUtils.getJwtFromCookieRequest(request);
            if (StringUtils.hasText(token)) {
                Claims claims = tokenProvider.validateToken(token, request, response);
                String idToken = (String) claims.get("idToken");
                Date expireDate = claims.getExpiration();
                InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                        .idToken(idToken)
                        .expiryTime(expireDate)
                        .build();
                invalidatedTokenRepository.save(invalidatedToken);
                httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
                CookieUtils.deleteCookie(request, response, CommonConstants.COOKIE_AUTH_TOKEN);
            } else {
                throw new ServiceBusinessExceptional(EResponseStatus.TOKEN_NOT_VALIDATE.getLabel(), EResponseStatus.TOKEN_NOT_VALIDATE.getCode());
            }
        } catch (ServiceBusinessExceptional ex) {
            log.error("Exception occurred while persisting AuthenticateService:logout logout fail , Exception message {}", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
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
        } catch (Exception ex) {
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

    @Override
    public void authorizePage(String permission) {
        try {
            log.info("AuthenticateService:authorizePage execution started.");
            UserPrincipal userPrincipal = getCurrentUser();
            User user = userRepository.findById(userPrincipal.getId())
                    .orElseThrow(() -> new ServiceBusinessExceptional(EResponseStatus.NOT_FOUND_USER.getLabel(),
                            EResponseStatus.NOT_FOUND_USER.getCode()));

            for (Role role : user.getRoles()) {
                if (role.getPermissions().contains(permission)) {
                    return;
                }
            }
            throw new ServiceBusinessExceptional(EResponseStatus.NOT_AUTHORIZE.getLabel(), EResponseStatus.NOT_AUTHORIZE.getCode());
        }catch (ServiceBusinessExceptional ex){
            throw ex;
        }catch (Exception ex){
            log.error("Exception occurred while persisting AuthenticateService:getRoleAuthorize to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.NOT_AUTHORIZE.getLabel(), EResponseStatus.NOT_AUTHORIZE.getCode());
        }
    }

    private void attemptLogin(HttpServletRequest request) {
        SessionUtils.getSessionAttribute(request, SESSION_ATTEMPT_LOGIN)
                .ifPresent(numberAttempt -> {
                    if (numberAttempt instanceof Integer attempts && attempts > 5) {
                        throw new LoginAttemptsException(ATTEMPT_LOGIN_MESSAGE);
                    } else if (!(numberAttempt instanceof Integer)) {
                        SessionUtils.removeSessionAttribute(request, SESSION_ATTEMPT_LOGIN);
                    }
                });
    }

    private void handleFailedLoginAttempt(HttpServletRequest request) {
        SessionUtils.memoryAttemptLoginSession(request);
    }

    private CustomValidationException createCustomValidationException(Object target, String field, EResponseStatus status) {
        BindingResult bindingResult = new BeanPropertyBindingResult(target, "loginRequest");
        bindingResult.addError(new FieldError("loginRequest", field, status.getLabel()));
        return new CustomValidationException(bindingResult, status.getCode());
    }
}
