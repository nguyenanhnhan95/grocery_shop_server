package com.example.grocery_store_sales_online.model.shop;

import com.example.grocery_store_sales_online.model.common.Model;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "promotion",uniqueConstraints = @UniqueConstraint(columnNames ={"code"}),
        indexes = {@Index(columnList = "code")})
public class Promotion extends Model {
    private String name;
    private String code;
    private String description;
    private double discountRate;
    private Date startDate;
    private Date endDate;
    public Promotion() {
    }
    @QueryProjection
    public Promotion(String code) {
        this.code = code;
    }
}
