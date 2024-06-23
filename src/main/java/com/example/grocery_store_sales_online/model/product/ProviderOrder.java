package com.example.grocery_store_sales_online.model.product;

import com.example.grocery_store_sales_online.enums.EPayment;
import com.example.grocery_store_sales_online.enums.ETypePayment;
import com.example.grocery_store_sales_online.model.common.Model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "provider_order")
@Getter
@Setter
public class ProviderOrder extends Model {
    private String note;
    private String nameEmployee;
    @Enumerated(EnumType.STRING)
    private EPayment payment;

    @Enumerated(EnumType.STRING)
    private ETypePayment typePayment;
    @ManyToOne
    private ProductProvider productProvider ;
    @OneToMany(mappedBy = "providerOrder",fetch = FetchType.LAZY)
    private List<ItemProvider> itemProviders=new ArrayList<>();

}
