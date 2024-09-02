package com.example.grocery_store_sales_online.model.address;

import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "provinces")
@Setter
@Getter
public class Provinces {

    @Id
    private String code;
    private String name;
    private String code_name;

    public Provinces() {
    }


    @ManyToOne
    @JoinColumn(name = "administrative_unit_id", referencedColumnName = "id")
    private AdministrativeUnits administrativeUnits;
}

