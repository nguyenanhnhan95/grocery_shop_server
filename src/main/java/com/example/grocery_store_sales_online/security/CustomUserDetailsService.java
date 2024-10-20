package com.example.grocery_store_sales_online.security;

import com.example.grocery_store_sales_online.enums.AuthProvider;
import com.example.grocery_store_sales_online.enums.EAccountStatus;
import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.exception.ServiceBusinessExceptional;
import com.example.grocery_store_sales_online.model.person.SocialProvider;
import com.example.grocery_store_sales_online.model.person.User;

import com.example.grocery_store_sales_online.service.impl.EmployeeServiceImpl;
import com.example.grocery_store_sales_online.service.ISocialProviderService;
import com.example.grocery_store_sales_online.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    private final UserServiceImpl userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email)  {
        try {
            log.info("CustomUserDetailsService:loadUserByUsername execution started.");
            User user = userService.findByEmail(email)
                    .orElseGet(() -> userService.findByNameLogin(email)
                            .orElseThrow(() -> new UsernameNotFoundException("User not found with : " + email)));

            if (!user.getAccountStatus().equals(EAccountStatus.ACTIVATED)) {
                throw new DisabledException(EResponseStatus.ACCOUNT_DISABLE.getLabel());
            }
            return UserPrincipal.create(user, user.getRoles());
        }catch (DisabledException ex){
            throw ex;
        }catch (UsernameNotFoundException ex){
            log.error("Exception occurred while persisting CustomUserDetailsService:loadUserByUsername login fail , Exception message {}", ex.getMessage());
            throw ex;
        }catch (Exception ex){
            log.error("Exception occurred while persisting CustomUserDetailsService:loadUserByUsername login fail , Exception message {}", ex.getMessage());
            throw new BadCredentialsException("Đang nhập thất bại");
        }

    }

}
