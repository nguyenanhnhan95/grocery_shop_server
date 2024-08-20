package com.example.grocery_store_sales_online.model.person;

import com.example.grocery_store_sales_online.enums.ETypeCustomer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User extends Person implements Serializable {
    private static final long serialVersionUID=1479437896339057579L;
    private Boolean emailVerified = false;

    @Column
    @Enumerated(EnumType.STRING)
    private ETypeCustomer typeCustomer;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SocialProvider> authenticationProviders = new ArrayList<>();

    private boolean enable;
    @ManyToMany(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",joinColumns = {@JoinColumn(name="user_id")
    },inverseJoinColumns = {@JoinColumn(name="role_id")})
    private Set<Role> roles = new HashSet<>();
    @Override
    public String toString() {
        return super.toString()+""+this.getName()+""+this.getRoles();
    }


}
