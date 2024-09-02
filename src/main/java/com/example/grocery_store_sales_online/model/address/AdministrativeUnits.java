package com.example.grocery_store_sales_online.model.address;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "administrative_units")
@Setter
@Getter
public class AdministrativeUnits {

    @Id
    private int id;
    private String name;
    private String short_name;
}

