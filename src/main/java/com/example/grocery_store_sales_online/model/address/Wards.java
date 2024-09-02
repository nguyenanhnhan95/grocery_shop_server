package com.example.grocery_store_sales_online.model.address;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "wards")
@Setter
@Getter
public class Wards {

    @Id
    private String code;
    private String name;
    private String code_name;
    @ManyToOne
    @JoinColumn(name="district_code", referencedColumnName = "code")
    private Districts districts;
    @ManyToOne
    @JoinColumn (name = "administrative_unit_id", referencedColumnName = "id")
    private AdministrativeUnits administrativeUnits;
}

