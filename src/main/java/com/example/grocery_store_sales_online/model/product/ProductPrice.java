package com.example.grocery_store_sales_online.model.product;

import com.example.grocery_store_sales_online.model.common.Model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Table(name = "product_price")
@Entity
public class ProductPrice extends Model {
    private Long price;
    private Date startDate;
    private Date endDate;
    @ManyToOne
    private Product product;
}
