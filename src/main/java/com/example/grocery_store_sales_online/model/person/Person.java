package com.example.grocery_store_sales_online.model.person;

import com.example.grocery_store_sales_online.enums.EAccountStatus;
import com.example.grocery_store_sales_online.enums.ECountry;
import com.example.grocery_store_sales_online.enums.EScreenTheme;
import com.example.grocery_store_sales_online.model.address.Districts;
import com.example.grocery_store_sales_online.model.address.Provinces;
import com.example.grocery_store_sales_online.model.address.Wards;
import com.example.grocery_store_sales_online.model.common.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class Person extends Model {
    private String name;
    private String nameLogin;
    private String phone;
    @Column(unique=true)
    private String email;
    private String avatar;
    private Date birthOfDate;
    private String idCard;
    private Date lastLogin;
    private String householdRegistration;

    // Dia chi hien tai
    private String address;

    @ManyToOne
    private Provinces provinces;

    @ManyToOne
    private Districts districts;

    @ManyToOne
    private Wards wards;
    @Column
    @Enumerated(EnumType.STRING)
    private ECountry country;
    @Column
    @Enumerated(EnumType.STRING)
    private EScreenTheme screenTheme;
    @JsonIgnore
    private String password;
    @Enumerated(EnumType.STRING)
    private EAccountStatus accountStatus;
    @Transient
    public boolean isActive(){
        return  EAccountStatus.ACTIVATED.equals(this.getAccountStatus());
    }
    @PrePersist
    void preInsert() {
        if (this.screenTheme == null) {
            this.screenTheme = EScreenTheme.LIGHT;
        }
    }
}
