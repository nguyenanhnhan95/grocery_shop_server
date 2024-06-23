package com.example.grocery_store_sales_online.security.oauth2;

import com.example.grocery_store_sales_online.enums.AuthProvider;
import com.example.grocery_store_sales_online.enums.ETypeCustomer;
import com.example.grocery_store_sales_online.enums.EAccountStatus;
import com.example.grocery_store_sales_online.enums.ErrorCode;
import com.example.grocery_store_sales_online.exception.ActiveException;
import com.example.grocery_store_sales_online.exception.OAuth2AuthenticationProcessingException;
import com.example.grocery_store_sales_online.model.account.Role;
import com.example.grocery_store_sales_online.model.person.User;
import com.example.grocery_store_sales_online.security.UserPrincipal;
import com.example.grocery_store_sales_online.security.oauth2.user.OAuth2UserInfo;
import com.example.grocery_store_sales_online.security.oauth2.user.OAuth2UserInfoFactory;
import com.example.grocery_store_sales_online.service.email.EmailSenderService;
import com.example.grocery_store_sales_online.service.role.RoleService;
import com.example.grocery_store_sales_online.service.user.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserService userService;
    private final RoleService roleService;

    private final EmailSenderService emailSenderService;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }
    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) throws MessagingException, IOException {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if(StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }

        User user = userService.findByEmail(oAuth2UserInfo.getEmail());
        if(user!=null) {
            if(!user.getProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
                        user.getProvider() + " account. Please use your " + user.getProvider() +
                        " account to login.");
            }
            if(!user.isActive()){
                throw  new ActiveException(ErrorCode.ACCOUNT_NOT_ACTIVE);
            }
            user = updateExistingUser(user, oAuth2UserInfo);
        } else {
            user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }

        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }
    private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) throws MessagingException, IOException {
        User user = new User();
        user.setProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
        user.setProviderId(oAuth2UserInfo.getId());
        user.setName(oAuth2UserInfo.getName());
        user.setEmail(oAuth2UserInfo.getEmail());
        user.setAccountStatus(EAccountStatus.ACTIVATED);
        user.setImageUrl(oAuth2UserInfo.getImageUrl());
        user.setTypeCustomer(ETypeCustomer.Normal);
        user.setLastLogin(new Date());
        Set<Role> setRole = new HashSet<>();
        Optional<Role> roleUser = roleService.findByAlias("ROLE_USER");
        if(roleUser.isPresent()){
            setRole.add(roleUser.get());
            user.setRoles(setRole);
        }
        if(AuthProvider.google.toString().equals(AuthProvider.google.toString())){
            emailSenderService.sendEmail(user.getEmail(), user.getName(), "Bạn đã đăng ký thành công tài khoản ");
        }
        return userService.saveModel(user);
    }

    private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setName(oAuth2UserInfo.getName());
        existingUser.setImageUrl(oAuth2UserInfo.getImageUrl());
        existingUser.setLastLogin(new Date());
        return userService.saveModel(existingUser);
    }
}
