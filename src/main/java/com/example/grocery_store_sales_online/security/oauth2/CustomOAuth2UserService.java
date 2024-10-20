package com.example.grocery_store_sales_online.security.oauth2;

import com.example.grocery_store_sales_online.enums.*;
import com.example.grocery_store_sales_online.exception.OAuth2AuthenticationProcessingException;
import com.example.grocery_store_sales_online.model.person.Role;
import com.example.grocery_store_sales_online.model.person.SocialProvider;
import com.example.grocery_store_sales_online.model.person.User;
import com.example.grocery_store_sales_online.repository.role.impl.RoleRepository;
import com.example.grocery_store_sales_online.repository.socialProvider.impl.SocialProviderRepository;
import com.example.grocery_store_sales_online.repository.user.impl.UserRepository;
import com.example.grocery_store_sales_online.security.UserPrincipal;
import com.example.grocery_store_sales_online.security.oauth2.user.OAuth2UserInfo;
import com.example.grocery_store_sales_online.security.oauth2.user.OAuth2UserInfoFactory;
import com.example.grocery_store_sales_online.service.IEmailSenderService;
import com.example.grocery_store_sales_online.service.IImageService;
import com.example.grocery_store_sales_online.service.impl.BaseServiceImpl;
import com.example.grocery_store_sales_online.service.impl.RoleServiceImpl;
import com.example.grocery_store_sales_online.utils.CommonConstants;
import com.example.grocery_store_sales_online.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final SocialProviderRepository socialProviderRepository;
    private final IEmailSenderService emailSenderService;
    private final IImageService imageService;
    @Value("${filestore.folder.image.avatar}")
    private String folderImage;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest)  {
        try {
            OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
            return processOAuth2(oAuth2UserRequest, oAuth2User);
        } catch (OAuth2AuthenticationProcessingException ex) {
            log.error("Exception occurred while persisting CustomOAuth2UserService:loadUser login fail , Exception message {}", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Exception occurred while persisting CustomOAuth2UserService:loadUser login fail , Exception message {}", ex.getMessage());
            throw new InternalAuthenticationServiceException(EResponseStatus.LOGIN_OAUTH2_FAIL.getLabel());
        }
    }

    private OAuth2User processOAuth2(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User)  {
        try {
            OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
            SocialProvider socialProvider = new SocialProvider();
            socialProvider.setProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
            socialProvider.setProviderId(oAuth2UserInfo.getId());
            Optional<SocialProvider> findSocialProvider = socialProviderRepository.findByProviderAndIdProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()), oAuth2UserInfo.getId());
            if (findSocialProvider.isPresent()) {
                if (findSocialProvider.get().getUser() != null) {
                    User user = updateExistingUser(findSocialProvider.get().getUser(), oAuth2UserInfo);
                    return UserPrincipal.create(user,  user.getRoles());
                }
                socialProviderRepository.deleteById(findSocialProvider.get().getId());
            }
            Optional<User> userOptional = userRepository.findByEmail(oAuth2UserInfo.getEmail());
            if (userOptional.isPresent()) {
                User user = this.updateSocialProviderUser(userOptional.get(), oAuth2UserInfo, socialProvider);
                return UserPrincipal.create(user,  user.getRoles());
            }

            User user = registerNewUser(oAuth2UserInfo, socialProvider);
            return UserPrincipal.create(user,  oAuth2User.getAttributes());
        }catch (Exception ex){
            throw new OAuth2AuthenticationProcessingException(EResponseStatus.LOGIN_OAUTH2_FAIL.getLabel());
        }

    }
    @Transactional
    public  User registerNewUser(OAuth2UserInfo oAuth2UserInfo, SocialProvider socialProvider)  {
        try {
            User user = new User();
            user.setName(oAuth2UserInfo.getName());
            user.setEmail(oAuth2UserInfo.getEmail());
            user.setAccountStatus(EAccountStatus.ACTIVATED);
            user.setAvatar(oAuth2UserInfo.getImageUrl());
            user.setEmailVerified(true);
            if (oAuth2UserInfo.getImageUrl() != null) {
                String pathImage = folderImage + CommonUtils.generateNameAlias(oAuth2UserInfo.getName()) + CommonConstants.UNDER_SCOPE + oAuth2UserInfo.getId();
                user.setAvatar(imageService.handleImageToServerByUrl(oAuth2UserInfo.getImageUrl(), pathImage));
            }
            user.setTypeCustomer(ETypeCustomer.Normal);
            user.setLastLogin(new Date());
            Set<Role> setRole = new HashSet<>();
            Optional<Role> roleUser = roleRepository.findByNameAndAlias(ECustomerRole.CUSTOMER_NEW.getLabel(), ERole.USER.getCode());
            if (roleUser.isPresent()) {
                setRole.add(roleUser.get());
                user.setRoles(setRole);
            }
            User saveUser = userRepository.saveModel(user);
            socialProvider.setUser(saveUser);
            socialProviderRepository.saveModel(socialProvider);
            if (AuthProvider.google.toString().equals(AuthProvider.google.toString())) {
                emailSenderService.notifyRegisterSuccess(user.getEmail());
            }
            return saveUser;
        }catch (Exception ex){
            throw new OAuth2AuthenticationProcessingException(EResponseStatus.LOGIN_OAUTH2_FAIL.getLabel());
        }
    }


    private User updateSocialProviderUser(User user, OAuth2UserInfo oAuth2UserInfo, SocialProvider socialProvider) {
        try {
            user.setLastLogin(new Date());
            socialProvider.setUser(user);
            if (user.getAvatar() == null) {
                String pathImage = folderImage + CommonUtils.generateNameAlias(oAuth2UserInfo.getName()) + CommonConstants.UNDER_SCOPE + oAuth2UserInfo.getId();
                user.setAvatar(imageService.handleImageToServerByUrl(oAuth2UserInfo.getImageUrl(), pathImage));
            }
            socialProviderRepository.saveModel(socialProvider);
            return userRepository.saveModel(user);
        }catch (Exception ex) {
            throw new OAuth2AuthenticationProcessingException(EResponseStatus.LOGIN_OAUTH2_FAIL.getLabel());
        }
    }

    private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        try {
            existingUser.setLastLogin(new Date());
            if (existingUser.getEmail() == null || !existingUser.getEmail().equals(oAuth2UserInfo.getEmail())) {
                existingUser.setEmail(oAuth2UserInfo.getEmail());
            }
            if (existingUser.getAvatar() == null) {
//            String extension = employee.getImageUrl().substring(employee.getImageUrl().lastIndexOf('.') + 1);
                String pathImage = folderImage + CommonUtils.generateNameAlias(oAuth2UserInfo.getName()) + CommonConstants.UNDER_SCOPE + oAuth2UserInfo.getId();
                existingUser.setAvatar(imageService.handleImageToServerByUrl(oAuth2UserInfo.getImageUrl(), pathImage));
            }
            return userRepository.saveModel(existingUser);

        } catch (Exception ex) {
            throw new OAuth2AuthenticationProcessingException(EResponseStatus.LOGIN_OAUTH2_FAIL.getLabel());

        }
    }
}
