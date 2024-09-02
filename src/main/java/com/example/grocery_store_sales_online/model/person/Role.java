package com.example.grocery_store_sales_online.model.person;

import com.example.grocery_store_sales_online.model.common.Model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="role", uniqueConstraints = @UniqueConstraint(columnNames ={"name"}),
indexes = {@Index(columnList = "name")})
@Getter
@Setter
public class Role extends Model implements Serializable {
    private static final long serialVersionUID=1L;
    private String name;
    private String alias;
    @Column(length = 500)
    private String description;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "role_permissions",joinColumns = {@JoinColumn(name="role_id")})
    private Set<String> permissions = new HashSet<>();
    public Role(){
        super();
    }

    public Role(String name, Set<String> permissions) {
        super();
        this.name = name;
        this.permissions = permissions;
    }


}
