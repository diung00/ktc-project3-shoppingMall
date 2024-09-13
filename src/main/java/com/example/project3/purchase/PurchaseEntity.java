package com.example.project3.purchase;

import com.example.project3.BaseEntity;
import com.example.project3.applyForBusiness.RequestStatus;
import com.example.project3.item.ItemEntity;
import com.example.project3.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "purchase")
@Setter
@Getter
public class PurchaseEntity extends BaseEntity {
    @ManyToOne
    private User user;

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
