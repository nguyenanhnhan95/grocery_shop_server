package com.example.grocery_store_sales_online.security.oauth2;

import com.example.grocery_store_sales_online.enums.AuthProvider;
import com.example.grocery_store_sales_online.enums.ETypeCustomer;
import com.example.grocery_store_sales_online.enums.EAccountStatus;
import com.example.grocery_store_sales_online.exception.OAuth2AuthenticationProcessingException;
import com.example.grocery_store_sales_online.model.person.Employee;
import com.example.grocery_store_sales_online.model.person.Role;
import com.example.grocery_store_sales_online.model.person.SocialProvider;
import com.example.grocery_store_sales_online.model.person.User;
import com.example.grocery_store_sales_online.security.UserPrincipal;
import com.example.grocery_store_sales_online.security.oauth2.user.OAuth2UserInfo;
import com.example.grocery_store_sales_online.security.oauth2.user.OAuth2UserInfoFactory;
import com.example.grocery_store_sales_online.service.IEmailSenderService;
import com.example.grocery_store_sales_online.service.IEmployeeService;
import com.example.grocery_store_sales_online.service.IImageService;
import com.example.grocery_store_sales_online.service.impl.RoleServiceImpl;
import com.example.grocery_store_sales_online.service.ISocialProviderService;
import com.example.grocery_store_sales_online.service.impl.UserServiceImpl;
import com.example.grocery_store_sales_online.utils.CommonConstants;
import com.example.grocery_store_sales_online.utils.CommonUtils;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;
    private final ISocialProviderService socialProviderService;
    private final IEmailSenderService emailSenderService;
    private final IEmployeeService employeeService;
    private final IImageService imageService;
    @Value("${filestore.folder.image.avatar}")
    private String folderImage;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) throws MessagingException, IOException {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }
        SocialProvider socialProvider = new SocialProvider();
        socialProvider.setProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
        socialProvider.setProviderId(oAuth2UserInfo.getId());
        Optional<SocialProvider> findSocialProvider = socialProviderService.findByProviderAndIdProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()), oAuth2UserInfo.getId());
        if (findSocialProvider.isPresent()) {
            if (findSocialProvider.get().getEmployee() != null) {
                Employee employee = this.updateExistingEmployee(findSocialProvider.get().getEmployee(), oAuth2UserInfo);
                return UserPrincipal.createEmployee(employee,oAuth2UserInfo.getId(), employee.getRoles());
            }
            if (findSocialProvider.get().getUser() != null) {
                User user = updateExistingUser(findSocialProvider.get().getUser(), oAuth2UserInfo);
                return UserPrincipal.create(user,oAuth2UserInfo.getId(),oAuth2User.getAttributes());
            }
            socialProviderService.deleteModel(findSocialProvider.get().getId());
        }
        Optional<Employee> employeeOptional = employeeService.findByEmail(oAuth2UserInfo.getEmail());
        if (employeeOptional.isPresent()) {
            Employee employee = this.updateSocialProviderEmployee(oAuth2UserInfo,employeeOptional.get(), socialProvider);
            return UserPrincipal.createEmployee(employee,oAuth2UserInfo.getId(), employee.getRoles());
        }
        Optional<User> userOptional = userService.findByEmail(oAuth2UserInfo.getEmail());
        if (userOptional.isPresent()) {
            User user = this.updateSocialProviderUser(userOptional.get(), socialProvider);
            return UserPrincipal.create(user, oAuth2UserInfo.getId(),oAuth2User.getAttributes());
        }

        User user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo, socialProvider);
        return UserPrincipal.create(user, oAuth2UserInfo.getId(),oAuth2User.getAttributes());
    }

    private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo, SocialProvider socialProvider) throws MessagingException, IOException {
        User user = new User();
        user.setName(oAuth2UserInfo.getName());
        user.setEmail(oAuth2UserInfo.getEmail());
        user.setAccountStatus(EAccountStatus.ACTIVATED);
        user.setAvatar(oAuth2UserInfo.getImageUrl());
        if(oAuth2UserInfo.getImageUrl()!=null ){
            String pathImage = folderImage+CommonConstants.SLASH+CommonUtils.generateNameAlias(oAuth2UserInfo.getName())+CommonConstants.UNDER_SCOPE+oAuth2UserInfo.getId();
            user.setAvatar(imageService.handleImageToServerByUrl(oAuth2UserInfo.getImageUrl(),pathImage));
        }
        user.setTypeCustomer(ETypeCustomer.Normal);
        user.setLastLogin(new Date());
        Set<Role> setRole = new HashSet<>();
        Optional<Role> roleUser = roleService.findByAlias("ROLE_USER");
        if (roleUser.isPresent()) {
            setRole.add(roleUser.get());
            user.setRoles(setRole);
        }
        User saveUser = userService.saveModel(user);
        socialProvider.setUser(saveUser);
        socialProviderService.saveModel(socialProvider);
        if (AuthProvider.google.toString().equals(AuthProvider.google.toString())) {
            emailSenderService.notifyRegisterSuccess(user.getEmail());
        }
        return saveUser;
    }

    private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setLastLogin(new Date());
        if (existingUser.getEmail() == null || !existingUser.getEmail().equals(oAuth2UserInfo.getEmail())) {
            existingUser.setEmail(oAuth2UserInfo.getEmail());
        }
        return userService.saveModel(existingUser);
    }

    private Employee updateSocialProviderEmployee(OAuth2UserInfo oAuth2UserInfo, Employee employee, SocialProvider socialProvider) {
        employee.setLastLogin(new Date());
        if(employee.getName()==null){
            employee.setName(oAuth2UserInfo.getName());
        }
        if(employee.getAvatar()==null ){
//            String extension = employee.getImageUrl().substring(employee.getImageUrl().lastIndexOf('.') + 1);
            String pathImage = folderImage+CommonConstants.SLASH+ CommonUtils.generateNameAlias(oAuth2UserInfo.getName()) +CommonConstants.UNDER_SCOPE+oAuth2UserInfo.getId();
            employee.setAvatar(imageService.handleImageToServerByUrl(oAuth2UserInfo.getImageUrl(),pathImage));
        }
        socialProvider.setEmployee(employee);
        socialProviderService.saveModel(socialProvider);
        return employeeService.saveModel(employee);
    }

    private User updateSocialProviderUser(User user, SocialProvider socialProvider) {
        user.setLastLogin(new Date());
        socialProvider.setUser(user);
        socialProviderService.saveModel(socialProvider);
        return userService.saveModel(user);
    }

    private Employee updateExistingEmployee(Employee employee, OAuth2UserInfo oAuth2UserInfo) {
        employee.setLastLogin(new Date());
        if (employee.getEmail() == null || !employee.getEmail().equals(oAuth2UserInfo.getEmail())) {
            employee.setEmail(oAuth2UserInfo.getEmail());
        }
        return employeeService.saveModel(employee);
    }
    private void checkAccountStatus(EAccountStatus status){
        if(!EAccountStatus.ACTIVATED.equals(status)){
            throw new OAuth2AuthenticationProcessingException("Tài khoản đã khóa .");
        }
    }
}
