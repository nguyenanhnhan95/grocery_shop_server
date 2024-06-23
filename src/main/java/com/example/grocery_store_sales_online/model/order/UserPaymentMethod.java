package com.example.grocery_store_sales_online.model.order;

import com.example.grocery_store_sales_online.enums.ETypePayment;
import com.example.grocery_store_sales_online.model.common.Model;
import com.example.grocery_store_sales_online.model.person.User;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Table(name = "user_payment_method")
public class UserPaymentMethod extends Model {
    private String accountNumber;
    private String provider;
    private boolean flag;

    private Date expiryDate;
    @ManyToOne
    private User user;
    @Enumerated(EnumType.STRING)
    private ETypePayment typePayment;

}
