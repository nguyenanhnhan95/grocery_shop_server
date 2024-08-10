package com.example.grocery_store_sales_online.security;

import com.example.grocery_store_sales_online.config.AppProperties;
import com.example.grocery_store_sales_online.enums.AuthProvider;
import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.exception.InvalidException;
import com.example.grocery_store_sales_online.exception.ServiceBusinessExceptional;
import com.example.grocery_store_sales_online.model.InvalidatedToken;
import com.example.grocery_store_sales_online.repository.token.InvalidatedTokenRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.DecodingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class TokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
    private final   AppProperties appProperties;
    private final InvalidatedTokenRepository invalidatedTokenRepository;
    public String createToken(Authentication authentication, AuthProvider authProvider,boolean keepLogin) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Date expiryDate = new Date();
        if(keepLogin){
            expiryDate=new Date(expiryDate.getTime() + appProperties.getAuth().getTokenExpirationMsec());
        }else{
            expiryDate = new Date(expiryDate.getTime() +60*60*   1000);
        }
        return Jwts.builder()
                .setSubject(Long.toString(userPrincipal.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
//                .claim("scope",buildScope(userPrincipal))
                .claim("provider",authProvider.toString())
                .claim("idToken",UUID.randomUUID().toString())
                .signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret())
                .compact();
    }
    public void logout(String token) {
        try {
            log.info("TokenProvider:logout execution started.");
            if (StringUtils.hasText(token)) {
                Claims claims = validateToken(token);
                String idToken =(String) claims.get("idToken");
                Date expireDate = claims.getExpiration();
                InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                        .idToken(idToken)
                        .expiryTime(expireDate)
                        .build();
                invalidatedTokenRepository.save(invalidatedToken);
            }
        }catch (Exception ex){
            log.error("Exception occurred while persisting TokenProvider:logout to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.LOGOUT_FAIL.getLabel(), EResponseStatus.LOGOUT_FAIL.getCode());
        }
    }
    public Claims getClaims(String token){
       return Jwts.parser()
                .setSigningKey(appProperties.getAuth().getTokenSecret())
                .parseClaimsJws(token)
                .getBody();
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(appProperties.getAuth().getTokenSecret())
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }
    public String getUserProviderFromToken(String token){
        Claims claims =Jwts.parser()
                .setSigningKey(appProperties.getAuth().getTokenSecret())
                .parseClaimsJws(token)
                .getBody();
        return (String) claims.get("provider");
    }

    public Claims validateToken(String authToken)  {
        try {
            Claims claims=Jwts.parser().setSigningKey(appProperties.getAuth().getTokenSecret()).parseClaimsJws(authToken).getBody();
            if(invalidatedTokenRepository.findByIdToken((String) claims.get("idToken")).isPresent()){
                throw new InvalidException(EResponseStatus.UNAUTHENTICATED.getLabel(),EResponseStatus.UNAUTHENTICATED.getCode());
            }
            else {
                Date dateExpire = claims.getExpiration();
                if(dateExpire.getTime()-new Date().getTime()<(10*60*1000)){
                    throw new InvalidException(EResponseStatus.REFRESH_TOKEN.getLabel(),EResponseStatus.REFRESH_TOKEN.getCode());
                }
            }
            return claims;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
            throw new InvalidException(EResponseStatus.UNAUTHENTICATED.getLabel(),EResponseStatus.UNAUTHENTICATED.getCode());
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
            throw new InvalidException(EResponseStatus.EXPIRED_TOKEN.getLabel(),EResponseStatus.EXPIRED_TOKEN.getCode());
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        } catch (DecodingException e){
            logger.error("Decoding error");
        }
        return null;
    }
    private String buildScope(UserPrincipal userPrincipal){
        StringJoiner stringJoiner = new StringJoiner(" ");
        if(!CollectionUtils.isEmpty(userPrincipal.getAuthorities())){
            userPrincipal.getAuthorities().forEach(s->stringJoiner.add(s.getAuthority()));
        }
        return stringJoiner.toString();
    }
}
