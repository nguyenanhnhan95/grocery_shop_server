package com.example.grocery_store_sales_online.model.order;

import com.example.grocery_store_sales_online.enums.EOrderStatus;
import com.example.grocery_store_sales_online.enums.ETypePayment;
import com.example.grocery_store_sales_online.model.common.Model;
import com.example.grocery_store_sales_online.model.person.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "shop_order")
public class ShopOrder extends Model {
    @ManyToOne
    private User user;
    @ManyToOne
    private ShippingMethod shippingMethod;
    @Enumerated(EnumType.STRING)
    private ETypePayment typePayment;

    @Enumerated(EnumType.STRING)
    private EOrderStatus orderStatus;

}
