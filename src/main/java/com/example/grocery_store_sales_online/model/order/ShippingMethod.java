package com.example.grocery_store_sales_online.model.order;

import com.example.grocery_store_sales_online.model.common.Model;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "shipping_method")
public class ShippingMethod extends Model {
    private String name;
    private Long price;
}
