package com.example.grocery_store_sales_online.model.review;

import com.example.grocery_store_sales_online.enums.ERatingValue;
import com.example.grocery_store_sales_online.model.common.Model;
import com.example.grocery_store_sales_online.model.order.OrderDetail;
import com.example.grocery_store_sales_online.model.person.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Table(name = "comment")
@Entity
public class Comment extends Model {
    private String title;
    @Enumerated(EnumType.STRING)
    private ERatingValue ratingValue;
    @OneToOne
    private User author;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "comment_like",joinColumns = {@JoinColumn(name="comment_id")})
    private Set<Integer> users =new HashSet<>();
    @ManyToOne
    private Comment parentComment;
    @ManyToOne
    private OrderDetail orderDetail;
}
