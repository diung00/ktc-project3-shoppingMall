package com.example.project3.shop;

import com.example.project3.BaseEntity;
import com.example.project3.item.ItemEntity;
import com.example.project3.user.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.sql.ast.tree.expression.SqlTuple;

import java.util.ArrayList;
import java.util.List;

@Entity
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
    private Status status;


    @OneToOne(mappedBy = "shop")
    private User owner;



    @ManyToMany
    @JoinTable(
            name = "shop_item",
            joinColumns = @JoinColumn(name = "shop_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<ItemEntity> items = new ArrayList<>();

}
