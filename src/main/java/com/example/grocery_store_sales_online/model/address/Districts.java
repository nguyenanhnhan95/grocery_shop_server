package com.example.grocery_store_sales_online.model.address;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "districts")
@Setter
@Getter
public class Districts {

    @Id
    private String code;
    private String name;
    private String code_name;
    @ManyToOne
    @JoinColumn(name = "province_code", referencedColumnName = "code")
    private Provinces provinces;
    @ManyToOne
    @JoinColumn (name = "administrative_unit_id", referencedColumnName = "id")
    private AdministrativeUnits administrativeUnits;
}

