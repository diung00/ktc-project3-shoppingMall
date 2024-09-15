package com.example.project3.item;

import com.example.project3.BaseEntity;
import com.example.project3.purchase.PurchaseEntity;
import com.example.project3.shop.ShopEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "item")
@Getter
@Setter
@NoArgsConstructor
public class ItemEntity extends BaseEntity {

    private String name;
    private String description;
    private String imageUrl;
    private Double price;
    private Integer stock;

    @ManyToOne(fetch = FetchType.LAZY)
    private ShopEntity shop;

    @ManyToMany(mappedBy = "items", fetch = FetchType.LAZY)
    private final List<PurchaseEntity> purchases = new ArrayList<>();
}
