package com.example.project3.shop.shopOpeningRequest;

import com.example.project3.BaseEntity;
import com.example.project3.shop.ShopStatus;
import com.example.project3.user.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class ShopRequestEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    private ShopStatus status;

    private String reason;
}
