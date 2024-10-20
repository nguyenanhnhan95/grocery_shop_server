package com.example.grocery_store_sales_online.components;

import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.exception.ServiceBusinessExceptional;
import com.example.grocery_store_sales_online.model.person.Role;
import com.example.grocery_store_sales_online.model.person.SocialProvider;
import com.example.grocery_store_sales_online.model.person.User;
import com.example.grocery_store_sales_online.repository.socialProvider.impl.SocialProviderRepository;
import com.example.grocery_store_sales_online.repository.user.impl.UserRepository;
import com.example.grocery_store_sales_online.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;



@Component
@RequiredArgsConstructor
@Slf4j
public class RequiredPermission {
    private final UserRepository userRepository;
    public boolean checkPermission(String permission){
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(principal instanceof UserPrincipal userPrincipal){
                User user = userRepository.findById(userPrincipal.getId())
                        .orElseThrow(() -> new ServiceBusinessExceptional(EResponseStatus.NOT_FOUND_USER.getLabel(),
                                EResponseStatus.NOT_FOUND_USER.getCode()));
                for (Role role: user.getRoles()) {
                    if(role.getPermissions().contains(permission)){
                        return true;
                    }
                }
            }
            return false;
        }catch (Exception ex){
            log.error("Exception occurred while persisting RequiredPermission:checkPermission  , Exception message {}", ex.getMessage());
            return false;
        }

    }
}
