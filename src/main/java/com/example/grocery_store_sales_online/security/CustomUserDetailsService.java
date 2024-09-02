package com.example.grocery_store_sales_online.security;

import com.example.grocery_store_sales_online.enums.AuthProvider;
import com.example.grocery_store_sales_online.model.person.Employee;
import com.example.grocery_store_sales_online.model.person.SocialProvider;
import com.example.grocery_store_sales_online.model.person.User;

import com.example.grocery_store_sales_online.service.impl.EmployeeServiceImpl;
import com.example.grocery_store_sales_online.service.ISocialProviderService;
import com.example.grocery_store_sales_online.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final EmployeeServiceImpl employeeService;
    private final ISocialProviderService socialProviderService;

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        log.info("CustomUserDetailsService:loadUserByUsername execution started.");
        Optional<User> user = userService.findByEmail(email);
        if (user.isPresent()) {
            List<SocialProvider> socialProviders = socialProviderService.findByIdUser(user.get().getId());
            SocialProvider socialProvider = socialProviders.stream()
                    .filter(each -> each.getProvider().equals(AuthProvider.local))
                    .findFirst()
                    .orElseThrow(() ->new UsernameNotFoundException("User not found with : " + email));
            return UserPrincipal.createUser(user.get(),socialProvider.getProviderId(), "ROLE_USER");
        } else {
            Optional<Employee> employee = employeeService.findByNameLogin(email);
            if(employee.isPresent()){
                List<SocialProvider> socialProviders = socialProviderService.findByIdEmployee(employee.get().getId());
                SocialProvider socialProvider = socialProviders.stream()
                        .filter(each -> each.getProvider().equals(AuthProvider.local))
                        .findFirst()
                        .orElseThrow(() ->new UsernameNotFoundException("User not found with : " + email));
                return UserPrincipal.createEmployee(employee.get(),socialProvider.getProviderId(), employee.get().getRoles());
            }
            throw new UsernameNotFoundException("User not found with : " + email);
        }
    }

//    @Transactional
//    public UserDetails loadUserById(Long id) {
//        User user = userService.findById(id).orElse(null);
//        if (user != null) {
//            return UserPrincipal.createUser(user, "ROLE_USER");
//        } else {
//            throw new ResourceNotFoundException("User", "id", id, EResponseStatus.USER_NOT_FOUND);
//        }
//    }
//    @Transactional
//    public UserDetails loadEmployeeById(Long id){
//        Employee employee =employeeService.findById(id).orElse(null);
//        if(employee!=null){
//            return UserPrincipal.createEmployee(employee,employee.getRoles());
//        }else {
//            throw new ResourceNotFoundException("Nhân viên", "id", id, EResponseStatus.USER_NOT_FOUND);
//        }
//    }

}
