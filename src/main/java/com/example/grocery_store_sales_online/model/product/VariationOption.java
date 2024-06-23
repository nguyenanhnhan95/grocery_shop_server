package com.example.grocery_store_sales_online.model.product;

import com.example.grocery_store_sales_online.model.common.Model;
import com.example.grocery_store_sales_online.model.product.Variation;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "variation_option")
public class VariationOption extends Model {
    private String name;
    private String description;
    @ManyToOne
    private Variation variation;
}
