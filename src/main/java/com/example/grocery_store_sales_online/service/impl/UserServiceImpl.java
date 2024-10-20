package com.example.grocery_store_sales_online.service.impl;

import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.enums.EScreenTheme;
import com.example.grocery_store_sales_online.exception.ServiceBusinessExceptional;
import com.example.grocery_store_sales_online.model.person.Role;
import com.example.grocery_store_sales_online.model.person.SocialProvider;
import com.example.grocery_store_sales_online.model.person.User;
import com.example.grocery_store_sales_online.payload.UserCurrent;
import com.example.grocery_store_sales_online.payload.UserScreeThemeOnly;
import com.example.grocery_store_sales_online.repository.socialProvider.impl.SocialProviderRepository;
import com.example.grocery_store_sales_online.repository.user.impl.UserRepository;
import com.example.grocery_store_sales_online.security.UserPrincipal;
import com.example.grocery_store_sales_online.service.IUserService;
import com.example.grocery_store_sales_online.utils.CookieUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.grocery_store_sales_online.utils.CommonConstants.COOKIE_THEME;
import static com.example.grocery_store_sales_online.utils.CommonConstants.EXPIRE_THEME_COOKIE;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserServiceImpl extends BaseServiceImpl implements IUserService {
    private final UserRepository userRepository;


    @Override
    public Optional<User> findByEmail(String email) {
        try {
            log.info("UserService:findByEmail execution started.");
            return userRepository.findByEmail(email);
        } catch (Exception ex) {
            log.error("Exception occurred while persisting UserService:findByEmail to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    public Boolean existsByEmail(String email) {
        try {
            log.info("UserService:existsByEmail execution started.");
            return findByEmail(email) != null;
        } catch (Exception ex) {
            log.error("Exception occurred while persisting UserService:existsByEmail to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    public UserCurrent getInformationUserCurrent(UserPrincipal userPrincipal) {
        try {
            log.info("UserServiceImpl:getInformationUserCurrent execution started.");

            if (userPrincipal == null) {
                throw new ServiceBusinessExceptional(EResponseStatus.NOT_FOUND_USER.getLabel(),
                        EResponseStatus.NOT_FOUND_USER.getCode());
            }


            User user = userRepository.findById(userPrincipal.getId())
                    .orElseThrow(() -> new ServiceBusinessExceptional(EResponseStatus.NOT_FOUND_USER.getLabel(),
                            EResponseStatus.NOT_FOUND_USER.getCode()));
            Set<String> roles = user.getRoles().stream()
                    .map(Role::getAlias)
                    .collect(Collectors.toSet());

            Set<String> permissions = user.getRoles().stream()
                    .flatMap(role -> role.getPermissions().stream())
                    .collect(Collectors.toSet());

            return UserCurrent.builder()
                    .id(userPrincipal.getId())
                    .name(userPrincipal.getName())
                    .avatar(userPrincipal.getAvatar())
                    .roles(roles)
                    .permission(permissions)
                    .screenTheme(userPrincipal.getScreenTheme())
                    .build();
        } catch (ServiceBusinessExceptional ex) {
            log.error("Exception occurred while persisting UserServiceImpl:getInformationUserCurrent  , Exception message {}", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Exception occurred while persisting UserServiceImpl:getInformationUserCurrent  , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.NOT_FOUND_USER.getLabel(),
                    EResponseStatus.NOT_FOUND_USER.getCode());
        }
    }

    @Override
    public String getModeTheme(UserPrincipal userPrincipal) {
        try {
            return userRepository.findById(userPrincipal.getId())
                    .map(user -> user.getScreenTheme().getLabel().toLowerCase())
                    .orElse(EScreenTheme.LIGHT.getLabel().toLowerCase());
        } catch (Exception ex) {
            return EScreenTheme.LIGHT.getLabel().toLowerCase();
        }
    }

    @Override
    public User findById(Long id) {
        log.info("UserService:findById execution started.");
        try {
            return userRepository.findById(id)
                    .orElseThrow(() ->
                            new ServiceBusinessExceptional(
                                    EResponseStatus.NOT_FOUND_BY_ID.getLabel(),
                                    EResponseStatus.NOT_FOUND_BY_ID.getCode()
                            )
                    );
        } catch (ServiceBusinessExceptional ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Exception occurred while persisting UserService:findById to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    public User saveModel(User user) {
        try {
            log.info("UserService:saveModel execution started.");
            user.setPersonCreate(user.getName());
            setMetaData(user);
            return userRepository.saveModel(user);
        } catch (Exception ex) {
            log.error("Exception occurred while persisting UserService:saveModel save to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.SAVE_FAIL.getLabel(), EResponseStatus.SAVE_FAIL.getCode());
        }
    }

    @Override
    public void changeScreenMode(HttpServletResponse response, Long id, UserScreeThemeOnly userScreeThemeOnly) {
        log.info("UserService: ChangeScreenMode execution started for user ID: {}", id);
        try {
            User user =userRepository.findById(id)
                    .orElseThrow(() -> new ServiceBusinessExceptional(
                            EResponseStatus.NOT_FOUND_USER.getLabel(),
                            EResponseStatus.NOT_FOUND_USER.getCode())
                    );

            user.setScreenTheme(userScreeThemeOnly.getScreenTheme());
            User userAfterUpdate=userRepository.saveModel(user);
            CookieUtils.addCookie(response, COOKIE_THEME,userAfterUpdate.getScreenTheme().getLabel().toLowerCase().trim(),EXPIRE_THEME_COOKIE);
        } catch (ServiceBusinessExceptional ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Exception occurred while persisting UserService:ChangeScreenMode save to change screen mode , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.CHANGE_SCREEN_LIGHT_FAIL.getLabel(), EResponseStatus.CHANGE_SCREEN_LIGHT_FAIL.getCode());
        }
    }

    @Override
    public Optional<User> findByNameLogin(String nameLogin) {
        try {
            log.info("UserServiceImpl:findByNameLogin execution started.");
            return userRepository.findByNameLogin(nameLogin);
        } catch (Exception ex) {
            log.error("Exception occurred while persisting UserServiceImpl:findByNameLogin to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    public List<User> findAllAble() {
        try {
            log.info("UserServiceImpl:findAllAble execution started.");
            return userRepository.findAll();
        } catch (Exception ex) {
            log.error("Exception occurred while persisting UserServiceImpl:findAllAble to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }
}
