package com.example.grocery_store_sales_online.security;

import com.example.grocery_store_sales_online.enums.EScreenTheme;
import com.example.grocery_store_sales_online.model.person.Role;
import com.example.grocery_store_sales_online.model.person.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

public class UserPrincipal implements OAuth2User, UserDetails {
    private Long id;
    private String email;
    private String name;
    private  String avatar;
    private String password;
    private EScreenTheme screenTheme;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public UserPrincipal(Long id,String name, String email,String avatar,String password,EScreenTheme screenTheme, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name=name;
        this.email = email;
        this.avatar=avatar;
        this.password=password;
        this.authorities = authorities;
        this.screenTheme=screenTheme;
    }

    public static UserPrincipal createUser(User  user,String role) {
        List<GrantedAuthority> authorities = Collections.
                singletonList(new SimpleGrantedAuthority(role));

        return new UserPrincipal(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAvatar(),
                user.getPassword(),
                user.getScreenTheme(),
                authorities
        );
    }
    public static UserPrincipal create(User  user,Set<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
//                Collections.singletonList(new SimpleGrantedAuthority(role));
        roles.forEach(role->authorities.add(new SimpleGrantedAuthority(role.getAlias())));

        return new UserPrincipal(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAvatar(),
                user.getPassword(),
                user.getScreenTheme(),
                authorities
        );
    }


    public static UserPrincipal create(User user, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = UserPrincipal.createUser(user,"ROLE_USER");
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }


    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return this.password;
    }


    @Override
    public <A> A getAttribute(String name) {
        return OAuth2User.super.getAttribute(name);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public String getAvatar() {
        return avatar;
    }

    public EScreenTheme getScreenTheme() {
        return screenTheme;
    }

}
