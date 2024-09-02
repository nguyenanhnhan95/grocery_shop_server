package com.example.grocery_store_sales_online.projection.person;

import com.example.grocery_store_sales_online.enums.EAccountStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class EmployeeProjection {
    private Long id;
    private String name;
    private String nameLogin;
    private String phone;
    private String email;
    private String avatar;
    private Date createDate;
    private EAccountStatus accountStatus;
    private List<Long> roles;
    private String idIdentification;
    private Date birthOfDate;
    private String password;
    private String address;
    private String provinces;
    private String districts;
    private String wards;

    public EmployeeProjection() {
    }
    @QueryProjection
    public EmployeeProjection(Long id, String name, String nameLogin, String email, String avatar, Date createDate, EAccountStatus accountStatus) {
        this.id = id;
        this.name = name;
        this.nameLogin = nameLogin;
        this.email = email;
        this.avatar = avatar;
        this.createDate = createDate;
        this.accountStatus = accountStatus;
    }
    @QueryProjection
    public EmployeeProjection(Long id, String avatar, String name, String nameLogin, String phone,String email,
                              EAccountStatus accountStatus, String idIdentification, Date birthOfDate,
                              String password, String address, String provinces, String districts,
                              String wards, List<Long> roles) {
        this.id = id;
        this.name = name;
        this.nameLogin = nameLogin;
        this.phone = phone;
        this.email = email;
        this.avatar = avatar;
        this.accountStatus = accountStatus;
        this.roles = roles;
        this.idIdentification = idIdentification;
        this.birthOfDate = birthOfDate;
        this.password = password;
        this.address = address;
        this.provinces = provinces;
        this.districts = districts;
        this.wards = wards;
    }

}
