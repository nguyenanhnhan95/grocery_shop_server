package com.example.grocery_store_sales_online.model.person;

import com.example.grocery_store_sales_online.enums.AuthProvider;
import com.example.grocery_store_sales_online.model.common.Model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "social_provider",uniqueConstraints = @UniqueConstraint(columnNames = {"provider_id"}))
@Getter
@Setter
public class SocialProvider extends Model {
    private String providerId;
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;
    @ManyToOne
    private User user;
}
