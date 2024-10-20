package com.example.grocery_store_sales_online.security.oauth2;

import com.example.grocery_store_sales_online.config.AppProperties;
import com.example.grocery_store_sales_online.enums.AuthProvider;
import com.example.grocery_store_sales_online.exception.BadRequestException;
import com.example.grocery_store_sales_online.security.TokenProvider;
import com.example.grocery_store_sales_online.utils.CommonConstants;
import com.example.grocery_store_sales_online.utils.CookieUtils;
import com.example.grocery_store_sales_online.utils.SessionUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

import static com.example.grocery_store_sales_online.utils.CommonConstants.SESSION_ATTEMPT_LOGIN;


@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final TokenProvider tokenProvider;

    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
    @Value("${app.cors.domainClient}")
    private String domainClient;

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        try {
            SessionUtils.removeSessionAttribute(request,SESSION_ATTEMPT_LOGIN);
            Optional<String> keepCookie = CookieUtils.getCookie(request, "keepLogin").map(Cookie::getValue);

            boolean flagKeep = false;

            if (keepCookie.isPresent() && keepCookie.get().trim().equals("true")) {
                flagKeep = true;
            }
            CookieUtils.deleteCookie(request,response,HttpCookieOAuth2AuthorizationRequestRepository.OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
            String token = tokenProvider.createToken(authentication,  flagKeep);
            CookieUtils.addCookie(response, CommonConstants.COOKIE_AUTH_TOKEN,token,CommonConstants.EXPIRE_TOKEN_COOKIE);
            return UriComponentsBuilder.fromUriString(domainClient)
//                    .queryParam("token", token)
                    .build().toUriString();
        } catch (Exception ex) {
            httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
            log.error("Exception occurred while persisting OAuth2AuthenticationSuccessHandler:determineTargetUrl , Exception message {}", ex.getMessage());
            return UriComponentsBuilder.fromUriString(domainClient).build().toUriString();
        }


    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

//    private boolean isAuthorizedRedirectUri(String uri) {
//        URI clientRedirectUri = URI.create(uri);
//
//        return appProperties.getOauth2().getAuthorizedRedirectUris()
//                .stream()
//                .anyMatch(authorizedRedirectUri -> {
//                    // Only validate host and port. Let the clients use different paths if they want to
//                    URI authorizedURI = URI.create(authorizedRedirectUri);
//                    if (authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
//                            && authorizedURI.getPort() == clientRedirectUri.getPort()) {
//                        return true;
//                    }
//                    return false;
//                });
//    }
}
