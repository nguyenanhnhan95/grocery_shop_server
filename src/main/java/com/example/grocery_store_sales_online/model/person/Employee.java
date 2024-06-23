package com.example.grocery_store_sales_online.model.person;

import com.example.grocery_store_sales_online.enums.*;
import com.example.grocery_store_sales_online.model.EducationalLevel;
import com.example.grocery_store_sales_online.model.account.Role;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "employee",uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
@Getter
@Setter
public class Employee extends Person  implements Serializable{
    private static final long serialVersionUID=1479437896339057579L;

    private String idCard;
    private String avatar;
    private boolean corruption;
    @Column(length = 1000)
    private String culture;
    @Column(length = 1000)
    private String language;
    private boolean firstLogin;
    @Enumerated(EnumType.STRING)
    private EAccountStatus accountStatus;
    @ManyToOne
    private EducationalLevel educationalLevel;
    @Column
    @Enumerated(EnumType.STRING)
    private EDepartment department;
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;
    @Enumerated(EnumType.STRING)
    private ECountry country;
    @Enumerated(EnumType.STRING)
    private EResignEmployeeStatus resignEmployeeStatus;
    @Enumerated(EnumType.STRING)
    private EEmployeeStatus employeeStatus;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "employee_role",joinColumns = {@JoinColumn(name="employee_id")
    },inverseJoinColumns = {@JoinColumn(name="role_id")})
    private Set<Role> roles = new HashSet<>();

    public Employee() {
        super();
    }

    @Override
    public String toString() {
        return super.toString()+""+this.getName()+""+this.getRoles();
    }

}
