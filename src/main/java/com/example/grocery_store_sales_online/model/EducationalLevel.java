package com.example.grocery_store_sales_online.model;

import com.example.grocery_store_sales_online.model.common.Model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="educational_level")
@Getter
@Setter
public class EducationalLevel extends Model {
    private String name;
    @Column(columnDefinition = "text")
    private String description;
}
