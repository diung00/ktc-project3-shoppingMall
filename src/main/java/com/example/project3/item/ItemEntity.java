package com.example.project3.item;

import com.example.project3.shop.ShopEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private String name;
    @Setter
    private String description;
    @Setter
    private String imageUrl;
    @Setter
    private Double price;
    @Setter
    private Integer stock;

    @ManyToMany(mappedBy = "items")
    private final List<ShopEntity> shops =new ArrayList<>();
}
