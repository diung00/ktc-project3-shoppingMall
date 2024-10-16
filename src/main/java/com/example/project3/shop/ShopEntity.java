package com.example.project3.shop;

import com.example.project3.BaseEntity;
import com.example.project3.item.ItemEntity;
import com.example.project3.purchase.PurchaseEntity;
import com.example.project3.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shop")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShopEntity extends BaseEntity {

    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private ShopStatus status;


    @OneToOne(mappedBy = "shop", fetch = FetchType.LAZY)
    private UserEntity owner;



    @OneToMany(mappedBy = "shop", fetch = FetchType.LAZY)
    private List<ItemEntity> items = new ArrayList<>();

    @OneToMany(mappedBy = "shop")
    private List<PurchaseEntity> purchases;

}
