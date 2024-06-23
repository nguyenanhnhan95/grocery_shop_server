package com.example.grocery_store_sales_online.model.cart;

import com.example.grocery_store_sales_online.model.common.Model;
import com.example.grocery_store_sales_online.model.person.User;
import com.example.grocery_store_sales_online.model.product.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "shopping_cart")
@Entity
public class ShoppingCart extends Model {
    private Long qty;
    @ManyToOne
    private User user;
    @ManyToOne
    private Product product;

}
