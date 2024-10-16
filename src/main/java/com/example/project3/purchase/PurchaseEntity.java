package com.example.project3.purchase;

import com.example.project3.BaseEntity;
import com.example.project3.item.ItemEntity;
import com.example.project3.shop.ShopEntity;
import com.example.project3.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "purchase")
@Setter
@Getter
public class PurchaseEntity extends BaseEntity {
    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private ShopEntity shop;

    @ManyToMany
    @JoinTable(
            name = "purchase_items",
            joinColumns = @JoinColumn(name = "purchase_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<ItemEntity> items = new ArrayList<>();


    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private PurchaseStatus status;

    private Double totalPrice;
}
