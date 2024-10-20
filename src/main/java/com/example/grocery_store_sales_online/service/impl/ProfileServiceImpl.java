package com.example.grocery_store_sales_online.service.impl;

import com.example.grocery_store_sales_online.enums.EAccountStatus;
import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.exception.ServiceBusinessExceptional;
import com.example.grocery_store_sales_online.model.person.Role;
import com.example.grocery_store_sales_online.model.person.SocialProvider;
import com.example.grocery_store_sales_online.model.person.User;
import com.example.grocery_store_sales_online.payload.UserCurrent;
import com.example.grocery_store_sales_online.payload.UserScreeThemeOnly;
import com.example.grocery_store_sales_online.repository.socialProvider.ISocialProviderRepository;
import com.example.grocery_store_sales_online.security.UserPrincipal;
import com.example.grocery_store_sales_online.service.IProfileService;
import com.example.grocery_store_sales_online.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileServiceImpl implements IProfileService {
    private  final ISocialProviderRepository socialProviderRepository;
    private final IUserService userService;
//    @Override
//    public UserCurrent getCurrentProfile(UserPrincipal userPrincipal) {
//        try {
//            log.info("ProfileService:getCurrentProfile execution started.");
//
//            if (userPrincipal == null) {
//                throw new ServiceBusinessExceptional(EResponseStatus.NOT_FOUND_USER.getLabel(),
//                        EResponseStatus.NOT_FOUND_USER.getCode());
//            }
//
//
//            SocialProvider socialProvider = socialProviderRepository.findByProviderId(userPrincipal.getIdProvider())
//                    .orElseThrow(() -> new ServiceBusinessExceptional(EResponseStatus.NOT_FOUND_USER.getLabel(),
//                            EResponseStatus.NOT_FOUND_USER.getCode()));
//
//            User user = socialProvider.getUser();
//
//
//            Set<String> roles = user.getRoles().stream()
//                    .map(Role::getAlias)
//                    .collect(Collectors.toSet());
//
//            Set<String> permissions = user.getRoles().stream()
//                    .flatMap(role -> role.getPermissions().stream())
//                    .collect(Collectors.toSet());
//
//            return UserCurrent.builder()
//                    .id(userPrincipal.getId())
//                    .name(userPrincipal.getName())
//                    .avatar(userPrincipal.getAvatar())
//                    .idProvider(userPrincipal.getIdProvider())
//                    .roles(roles)
//                    .permission(permissions)
//                    .screenTheme(userPrincipal.getScreenTheme())
//                    .build();
//        }catch (ServiceBusinessExceptional ex){
//            log.error("Exception occurred while persisting ProfileService:getCurrentProfile  , Exception message {}", ex.getMessage());
//            throw ex;
//        }catch (Exception ex){
//            log.error("Exception occurred while persisting ProfileService:getCurrentProfile  , Exception message {}", ex.getMessage());
//            throw new ServiceBusinessExceptional(EResponseStatus.NOT_FOUND_USER.getLabel(),
//                    EResponseStatus.NOT_FOUND_USER.getCode());
//        }
//    }


    @Override
    public List<Map<String, String>> listAccountStatus() {
        try {
            return Arrays.stream(EAccountStatus.values())
                    .map(status -> {
                        Map<String, String> map = new HashMap<>();
                        map.put("id", status.getCode());
                        map.put("name", status.getLabel());
                        return map;
                    })
                    .collect(Collectors.toList());

        } catch (Exception ex) {
            log.error("Exception occurred while persisting ProfileService:listAccountStatus to Enum EAccountStatus  , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

}
