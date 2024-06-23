package com.example.grocery_store_sales_online.model.order;

import com.example.grocery_store_sales_online.model.common.Model;
import com.example.grocery_store_sales_online.model.product.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "order_detail")
@Entity
public class OrderDetail extends Model {
    private Long qty;
    private Long price;
    @ManyToOne
    private Product product;
    @ManyToOne
    private ShopOrder shopOrder;

}
