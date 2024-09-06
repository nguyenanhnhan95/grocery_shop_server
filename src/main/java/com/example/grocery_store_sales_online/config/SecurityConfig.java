package com.example.grocery_store_sales_online.config;


import com.example.grocery_store_sales_online.enums.ERole;
import com.example.grocery_store_sales_online.security.CustomUserDetailsService;
import com.example.grocery_store_sales_online.security.LogoutSuccessHandler;
import com.example.grocery_store_sales_online.security.RestAuthenticationEntryPoint;
import com.example.grocery_store_sales_online.security.TokenAuthenticationFilter;
import com.example.grocery_store_sales_online.security.oauth2.CustomOAuth2UserService;
import com.example.grocery_store_sales_online.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.example.grocery_store_sales_online.security.oauth2.OAuth2AuthenticationFailureHandler;
import com.example.grocery_store_sales_online.security.oauth2.OAuth2AuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    private final CustomOAuth2UserService customOAuth2UserService;

    private final HandlerExceptionResolver handlerExceptionResolver;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    private  final LogoutSuccessHandler logoutSuccessHandler;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    private final String[] PUBLIC_ENDPOINTS={"/", "/error", "/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg","/auth/login","/auth/signup","/auth/logout","/auth/check-auth","/address/**",
            "/**/*.jpg", "/**/*.html", "/**/*.css", "/**/*.js", "/home/**", "/oauth2/**","/auth/login/**","/keep-login","/product-category/**","/test/**"};
    private final String[] ACTION_MANAGE_ADMIN={ERole.MANAGER.getLabel(),ERole.ADMIN.getLabel(),ERole.EMPLOYEE.getLabel()};
    private final String[] VIEW_MANAGE_ADMIN={ERole.MANAGER.getLabel(),ERole.ADMIN.getLabel(),ERole.EMPLOYEE.getLabel()};


    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(handlerExceptionResolver);
    }

    /*
      By default, Spring OAuth2 uses HttpSessionOAuth2AuthorizationRequestRepository to save
      the authorization request. But, since our service is stateless, we can't save it in
      the session. We'll save the request in a Base64 encoded cookie instead.
    */
    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authProvider);
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint( new RestAuthenticationEntryPoint()))
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(PUBLIC_ENDPOINTS)
                        .permitAll()
                        .requestMatchers("/employee/**").hasAnyAuthority(ERole.ADMIN.getLabel())
                        .requestMatchers("/role/**").hasAnyAuthority(ERole.ADMIN.getLabel())
                        .requestMatchers("/products-variation/**","/products-variation-option/**","/shop-promotion/**","/products/**","/auth/role/**")
                        .authenticated()
                        .requestMatchers("/user/**","/menu/admin-side/**","/profile/**")
                        .authenticated()
                )
//                .logout((logout)->logout.logoutUrl("/logout")
//                        .logoutSuccessHandler(logoutSuccessHandler)
//                        .permitAll())
                .oauth2Login((oauth2Login) -> oauth2Login
                        .authorizationEndpoint((authorizationEndpoint) ->
                                authorizationEndpoint.baseUri("/oauth2/authorize")
                                        .authorizationRequestRepository(cookieAuthorizationRequestRepository())
                        ).redirectionEndpoint((redirectionEndpoint) ->
                                redirectionEndpoint.baseUri("/oauth2/callback/*"))
                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint.userService(customOAuth2UserService))
                        .successHandler(oAuth2AuthenticationSuccessHandler)
                        .failureHandler(oAuth2AuthenticationFailureHandler)
                )
                .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);


        // Add our custom Token based authentication filter
        return http.build();
    }

}
