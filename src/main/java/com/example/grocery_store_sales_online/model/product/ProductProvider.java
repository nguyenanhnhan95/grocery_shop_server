package com.example.grocery_store_sales_online.model.product;

import com.example.grocery_store_sales_online.model.common.Model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product_provider")
@Getter
@Setter
public class ProductProvider extends Model {
    private String name;
    private String address;

}
