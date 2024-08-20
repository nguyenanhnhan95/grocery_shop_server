package com.example.grocery_store_sales_online.service.user.impl;

import com.example.grocery_store_sales_online.enums.EResponseStatus;
import com.example.grocery_store_sales_online.enums.EScreenTheme;
import com.example.grocery_store_sales_online.exception.ServiceBusinessExceptional;
import com.example.grocery_store_sales_online.model.person.User;
import com.example.grocery_store_sales_online.repository.user.impl.UserRepository;
import com.example.grocery_store_sales_online.service.base.impl.BaseService;
import com.example.grocery_store_sales_online.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
@RequiredArgsConstructor
@Service
@Slf4j
public class UserService extends BaseService implements IUserService {
    private final UserRepository userRepository;
    @Override
    public Optional<User> findByEmail(String email) {
        try {
            log.info("UserService:findByEmail execution started.");
            return userRepository.findByEmail(email);
        }catch (Exception ex){
            log.error("Exception occurred while persisting UserService:findByEmail to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    public Boolean existsByEmail(String email) {
        try {
            log.info("UserService:existsByEmail execution started.");
            return findByEmail(email) != null;
        }catch (Exception ex){
            log.error("Exception occurred while persisting UserService:existsByEmail to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.FETCH_DATA_FAIL.getLabel(), EResponseStatus.FETCH_DATA_FAIL.getCode());
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        try {
            log.info("UserService:findById execution started.");
            return userRepository.findById(id);
        }catch (Exception ex){
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
        }catch (Exception ex){
            log.error("Exception occurred while persisting UserService:saveModel save to database , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.SAVE_FAIL.getLabel(), EResponseStatus.SAVE_FAIL.getCode());
        }
    }

    @Override
    public void ChangeScreenMode(Long id, EScreenTheme screenTheme) {
        try {
            log.info("UserService:ChangeScreenMode execution started.");
            Optional<User> user = findById(id);
            user.ifPresent(value -> {
                value.setScreenTheme(screenTheme);
                this.saveModel(value);
            });
        }catch (Exception ex){
            log.error("Exception occurred while persisting UserService:ChangeScreenMode save to change screen mode , Exception message {}", ex.getMessage());
            throw new ServiceBusinessExceptional(EResponseStatus.CHANGE_SCREEN_LIGHT_FAIL.getLabel(), EResponseStatus.CHANGE_SCREEN_LIGHT_FAIL.getCode());
        }
    }
}
