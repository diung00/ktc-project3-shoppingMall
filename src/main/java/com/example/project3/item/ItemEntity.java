package com.example.project3.item;

import com.example.project3.BaseEntity;
import com.example.project3.shop.ShopEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemEntity extends BaseEntity {

    private String name;
    private String description;
    private String imageUrl;
    private Double price;
    private Integer stock;

    @ManyToMany(mappedBy = "items")
    private final List<ShopEntity> shops =new ArrayList<>();
}
