package com.example.grocery_store_sales_online.model;

import com.example.grocery_store_sales_online.model.common.Model;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Notification extends Model {
    private String text;
}
