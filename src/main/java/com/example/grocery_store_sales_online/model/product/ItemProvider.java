package com.example.grocery_store_sales_online.model.product;

import com.example.grocery_store_sales_online.model.common.Model;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "item_provider")
public class ItemProvider extends Model {
    private Long quantity;
    @OneToOne
    private Product product;
    @ManyToOne
    private ProviderOrder providerOrder;
    private double price;
}
